package clientAPI;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import it.unisa.dia.gas.plaf.jpbc.util.io.Base64;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.CipherParameters;

import params.BASPrivateKeyParameters;
import params.BASPublicKeyParameters;
import params.Keys;
import secCloudAPI.Data;
import serverAPI.Server;

public class Client {
	public static int clientNo = 1000;
	//private static BAS clientBAS;
	private static Server provServer;
	private AsymmetricCipherKeyPair keyPair; 
	private final int userID ;
	private String filename;
	private String foldername = "./resource/Client/";
	
	public Client() {
		userID = clientNo;
		clientNo++;
		filename = foldername + "User_privateKey_User_ID" + Integer.toString(userID);
		File f = new File(filename);
		if(f.exists() && !f.isDirectory()){
			Path currentPath = Paths.get(filename);
			try {
				byte[] skdata = null;
				skdata = Files.readAllBytes(currentPath);
		        Pairing pairing = PairingFactory.getPairing(provServer.serverBAS.parameters.getParameters());
		        Element g = provServer.serverBAS.parameters.getG();
		        Element sk = pairing.getZr().newRandomElement();
		        sk.setFromBytes(skdata);
		        
		        //System.out.println(Base64.encodeBytes(sk.toBytes()));
		        
		        Element pk = g.powZn(sk);
		    
		        
				keyPair = new AsymmetricCipherKeyPair(
			            new BASPublicKeyParameters(provServer.serverBAS.parameters, pk.getImmutable()),
			            new BASPrivateKeyParameters(provServer.serverBAS.parameters, sk.getImmutable())
			        );
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else {
			Keys nk = provServer.serverBAS.keyGen(filename);
			keyPair = new AsymmetricCipherKeyPair(
		            new BASPublicKeyParameters(nk.getParameters(), nk.getPk().getImmutable()),
		            new BASPrivateKeyParameters(nk.getParameters(), nk.getSk().getImmutable())
		        );
			byte[] privKeyBytes = nk.getSk().toBytes();
			byte[] pubKeyBytes = nk.getPk().toBytes();
			FileOutputStream fos;
			try {
				fos = new FileOutputStream(filename);
				//String prSave = Base64.encodeBytes(privKeyBytes);
				//System.out.println(Base64.encodeBytes(privKeyBytes));
				fos.write(privKeyBytes);
				fos.close();
		        
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Client.provServer.saveClientPublicKey(userID, pubKeyBytes);
		}
		
		
	}
	
	public CipherParameters getPubKey(){
		return keyPair.getPublic();
	}
	
	public int getUserID() {
		return userID;
	}
	public Data CreateData(){
		Data data = new Data();
		StringBuilder  sb = new StringBuilder("");
		sb.append(this.toString());
		data.setData(sb.toString());
		return data;
	}

	public Element getSign(Data currentData) {
		// TODO Auto-generated method stub
		return provServer.serverBAS.sign(currentData, keyPair.getPrivate());
		
	}

	public static void setServer(Server server) {
		// TODO Auto-generated method stub
		provServer = server;
	}


}
