package clientAPI;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.CipherParameters;

import params.BASPrivateKeyParameters;
import params.BASPublicKeyParameters;
import params.Keys;
import secCloudAPI.Data;
import serverAPI.Server;

public class Client {
	private static int clientNo = 1000;
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
		        
		        Element sk = pairing.getZr().newRandomElement();
		        sk.setFromBytes(skdata);
		        
		        filename = foldername + "User_publicKey_User_ID" + Integer.toString(userID);
		        currentPath = Paths.get(filename);
		        byte[] pkdata = null;
		        pkdata = Files.readAllBytes(currentPath);
		        Element pk = pairing.getG2().newRandomElement();
		        pk.setFromBytes(pkdata);
		        
				keyPair = new AsymmetricCipherKeyPair(
			            new BASPublicKeyParameters(provServer.serverBAS.parameters, pk.getImmutable()),
			            new BASPrivateKeyParameters(provServer.serverBAS.parameters, sk.getImmutable())
			        );
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("hi");
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
				fos.write(privKeyBytes);
				fos.close();
				filename = foldername + "User_publicKey_User_ID" + Integer.toString(userID);
				fos = new FileOutputStream(filename);
				fos.write(pubKeyBytes);
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
