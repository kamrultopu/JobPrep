/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prov.simpledb.client;

import com.amazonaws.services.simpledb.model.ReplaceableAttribute;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import prov.simpledb.crypto.BAS;
import prov.simpledb.crypto.BASPrivateKeyParameters;
import prov.simpledb.crypto.BASPublicKeyParameters;
import prov.simpledb.crypto.Base64;
import prov.simpledb.crypto.CryptoUtil;
import prov.simpledb.crypto.Keys;


/**
 *
 * @author Shams
 */
public class ProvEnabledSimpleDBClient {

    /**
     * @param args the command line arguments
     */
    private static String USER_AGENT = "Mozilla/5.0";
    String SUCCESS = "SUCCESS";
    String ERROR = "ERROR";
    private BAS clientBAS;
    private AsymmetricCipherKeyPair keyPair;
    private String userID;
    private Config config;

    private static final String[] FILE_HEADER = {"item", "name", "address"};

    public ProvEnabledSimpleDBClient(String userid) {
        clientBAS = new BAS();
        userID = userid;
        config = Config.Instanc();

    }

    public void genKeyPair() {
        File userHome = new File(System.getProperty("user.home"));
        //System.out.println(userHome);
        String filename = userHome.toString() + "\\baskeys\\User_privateKey_User_ID_" + userID;
        //String filename = config.KeyFolder() + "/User_privateKey_User_ID_" + userID;
        File f = new File(filename);
        if (f.exists() && !f.isDirectory()) {
            Path currentPath = Paths.get(filename);
            try {
                byte[] skdata = null;
                skdata = Files.readAllBytes(currentPath);
                Pairing pairing = PairingFactory.getPairing(clientBAS.parameters.getParameters());
                Element g = clientBAS.parameters.getG();

                // Generate the secret key
                Element sk = pairing.getZr().newRandomElement();
                sk.setFromBytes(skdata);
                // Generate the corresponding public key
                Element pk = g.powZn(sk);

                keyPair = new AsymmetricCipherKeyPair(
                        new BASPublicKeyParameters(clientBAS.parameters, pk.getImmutable()),
                        new BASPrivateKeyParameters(clientBAS.parameters, sk.getImmutable())
                );
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } else {
            Keys nk = clientBAS.keyGen(filename);
            keyPair = new AsymmetricCipherKeyPair(
                    new BASPublicKeyParameters(nk.getParameters(), nk.getPk().getImmutable()),
                    new BASPrivateKeyParameters(nk.getParameters(), nk.getSk().getImmutable())
            );
            byte[] privKeyBytes = nk.getSk().toBytes();

            FileOutputStream fos;
            try {
                fos = new FileOutputStream(filename);
                fos.write(privKeyBytes);
                fos.close();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    private void storeData(String url) {
        try {
            URL putUrl = new URL(url);
            String response = getResponseFromHttpReq(putUrl);
            String arr[] = response.split("#");

            if (arr[0].startsWith(SUCCESS)) {
                String provHashNoldHash = arr[1];
                String blockId = arr[2];

                Element signature = getSign(genHashChain(provHashNoldHash));
                URL aggrUrl = new URL(buildAggregateUrl(blockId, signature));

                String aggResponse = getResponseFromHttpReq(aggrUrl);
                //System.out.println("aggResponse = " + aggResponse);
            } else {
                System.out.println("Error Occured. Reason:" + arr[1]);
            }
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private String genHashChain(String provHashNoldHash) {
        return CryptoUtil.getHash(provHashNoldHash, config.HashAlgorithm());
    }

    public Element getSign(String currentData) {
        // TODO Auto-generated method stub
        return clientBAS.sign(currentData, keyPair.getPrivate());

    }

    private String getResponseFromHttpReq(URL url) throws IOException, IllegalStateException, Exception {

        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();

    }

    private String buildPutUrl(String attrUrl) {

        //String uri = config.PutUrl() + "?userId=" + userID + "&AWSAccessKeyId=AKIAIM6WZ7YZFJSQNVKQ&DomainName=shams.uab.edu&ItemName=1&noOfAttribute=3&Attribute.1.Name=name&Attribute.1.Value=zawoad&Attribute.2.Name=age&Attribute.2.Value=29&Attribute.3.Name=address&Attribute.3.Value=13679%20Bent%20Tree%20&Attribute.3.Replace=true";
        String uri = config.PutUrl() + "?userId=" + userID + "&"+attrUrl+"AWSAccessKeyId=AKIAIM6WZ7YZFJSQNVKQ&DomainName=patient";
        return uri;
    }

    private void processLine(CSVRecord csvRecord) {
        StringBuilder sb = new StringBuilder();
        sb.append("noOfAttribute=" + (FILE_HEADER.length - 1)+"&");
        sb.append("ItemName="+csvRecord.get(0)+"&");
        for (int i = 1; i < FILE_HEADER.length; i++) {
            sb.append("Attribute." + i + ".Name=" + FILE_HEADER[i]+"&");
            sb.append("Attribute." + i + ".Value=" + csvRecord.get(i)+"&");
            sb.append("Attribute." + i + ".Replace=true"+"&");
        }
        String putUrl = buildPutUrl(sb.toString());
        //System.out.println("putUrl = " + putUrl);
        storeData(putUrl);
      //  System.out.println("sb = " + sb.toString());
    }

    public void checkNoOfTransactionWithProv(long noOfTransaction)
    {
        for(int i=0; i < noOfTransaction; i++)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("noOfAttribute=" + (FILE_HEADER.length - 1) + "&");
            sb.append("ItemName=1&");
            sb.append("Attribute.1.Name=" + FILE_HEADER[1] + "&");
            sb.append("Attribute.1.Value=name-1&");
            sb.append("Attribute.1.Replace=true" + "&");
            sb.append("Attribute.2.Name=" + FILE_HEADER[2] + "&");
            sb.append("Attribute.2.Value=address-1&");
            sb.append("Attribute.2.Replace=true" + "&");
            String putUrl = buildPutUrl(sb.toString());
            System.out.println("putUrl = " + putUrl);
            storeData(putUrl);
        }
        
    }
    public void checkNoOfTransactionWithoutProv(long noOfTransaction)
    {
        for(int j=0; j < noOfTransaction; j++)
        {
            AmazonCredentials credential = new AmazonCredentials("AKIAIM6WZ7YZFJSQNVKQ");
            SimpleDBHelper simpledbHelper = new SimpleDBHelper(credential, "patient");
        
            int noOfAttribute = FILE_HEADER.length-1;
            List attributeList = new ArrayList(noOfAttribute);
            ReplaceableAttribute attribute = new ReplaceableAttribute(FILE_HEADER[1], "name-1", true);
            attributeList.add(attribute);
            attribute = new ReplaceableAttribute(FILE_HEADER[2], "address-1", true);
            attributeList.add(attribute);
            simpledbHelper.put("1", attributeList);
            System.out.println("updated "+j);
        }
        
    }
    public void uploadData(String fileName, long noOfRecord) throws FileNotFoundException, IOException {
        //System.out.println(fileName);
        
        System.out.println(fileName);
        long count = 0;
        for (CSVRecord csvRecord : new CSVParser(new FileReader(new File(fileName)), CSVFormat.RFC4180)) {
            count++;
            if(count > noOfRecord)
                break;
            processLine(csvRecord);

        }
    }
    
    public void regularUpload(String fileName, long noOfRecord) throws FileNotFoundException, IOException {
        
        long count = 0;
        for (CSVRecord csvRecord : new CSVParser(new FileReader(new File(fileName)), CSVFormat.RFC4180)) {
            AmazonCredentials credential = new AmazonCredentials("AKIAIM6WZ7YZFJSQNVKQ");
            SimpleDBHelper simpledbHelper = new SimpleDBHelper(credential, "patient");
        
            count++;
            if(count > noOfRecord)
                break;
            int noOfAttribute = FILE_HEADER.length-1;
            List attributeList = new ArrayList(noOfAttribute);
            for(int i=1; i <= noOfAttribute; i++)
            {
                String attName = FILE_HEADER[i];
                String attValue = csvRecord.get(i);
                Boolean replacable = Boolean.TRUE;
                ReplaceableAttribute attribute = new ReplaceableAttribute(attName, attValue, replacable);
                attributeList.add(attribute);
            }
            simpledbHelper.put(csvRecord.get(0), attributeList);
            System.out.println("uploaded: item"+csvRecord.get(0));
        }
    }
    private String buildAggregateUrl(String blockId, Element signature) {

        String uri = config.AggregateUrl() + "?blockId=" + blockId + "&sigma=" + Base64.encodeBytes(signature.toBytes());
        return uri;
    }

    public static void main(String[] args) {

        try {
            String username = args[2];
            String noOfUser = args[3];
            ProvEnabledSimpleDBClient client = new ProvEnabledSimpleDBClient(username);
            client.genKeyPair();
            int noOfRecord = Integer.parseInt(args[1]);
            String option = args[0];
            File userHome = new File(System.getProperty("user.home"));
            String fileName = userHome.toString()+ "\\sample_data.csv";
            //String fileName = "sample_data.csv";
            
            //Utility.genFile(10000, "sample_data.csv");
            long tn = System.currentTimeMillis();
            if(option.equals("pi")) {
                client.uploadData(fileName, noOfRecord);
            }
            else if(option.equals("ri"))
            {
                client.regularUpload(fileName, noOfRecord);
            }
            else if(option.equals("pu"))
            {
                client.checkNoOfTransactionWithProv(noOfRecord);
            }
            else if(option.equals("ru"))
            {
                client.checkNoOfTransactionWithoutProv(noOfRecord);
            }
            System.out.println("Username: " + username +", No of record: " + noOfRecord +", No of user: " + noOfUser);
            System.out.println("Option = "+option+"; time = " + (System.currentTimeMillis() - tn));
            
            //  client.storeData();
//            Element e = client.getSign("hello");
//            String sign = Base64.encodeBytes(e.toBytes());
//            System.out.println("e = " + sign);
//            
//            byte[] bytes = Base64.decode(sign);
//            //byte[] bytes = e.toBytes(); //this works
//            
//            BASSigner signer = new BASSigner(new SHA256Digest());
//            signer.init(true, client.getKeyPair().getPrivate());
//            Element r = signer.pairing.getG1().newRandomElement();
//            
//            int bytesRead = r.setFromBytes(bytes);
//            System.out.println("match ="+e.equals(r));
        } catch (Exception ex) {
            Logger.getLogger(ProvEnabledSimpleDBClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
