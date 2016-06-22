package BAS;

import engines.BASSigner;
import generators.BASKeyPairGenerator;
import generators.BASParametersGenerator;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import it.unisa.dia.gas.plaf.jpbc.util.io.Base64;

import java.io.IOException;
import java.util.ArrayList;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.digests.SHA256Digest;

import params.BASKeyGenerationParameters;
import params.BASParameters;
import params.BASPrivateKeyParameters;
import params.BASPublicKeyParameters;
import params.Keys;
import secCloudAPI.Data;
import serverAPI.Server;



public class BAS {
	//boolean  isAggregate;
	//int totalUser;
	//int totalMessage;
	//String propertiesFile;
	public BASParameters parameters;
	public String foldername = "./resource/Server/";
	//public AsymmetricCipherKeyPair[] keyPair = null; 
	
	
	public BAS() {
		Setup();
	}
	
	
	public void Setup(){
		BASParametersGenerator setup = new BASParametersGenerator();
		setup.init(PairingFactory.getPairingParameters(foldername +"params.properties"));
		parameters = setup.generateParameters();
	}
	
	public Keys keyGen(String filename){
		BASKeyPairGenerator keyGen = new BASKeyPairGenerator();
		keyGen.init(new BASKeyGenerationParameters(null,parameters));
		return keyGen.generateKeyPair(filename);
		//return keyGen.generateKeyPair();
	}
	public Element sign(Data currentData, CipherParameters privKey){
		byte[] bytes = currentData.getByte();
		BASSigner signer = new BASSigner(new SHA256Digest());
		signer.init(true, privKey);
		signer.update(bytes,0,bytes.length);
		int digestSize = signer.digest.getDigestSize();
        byte[] hash = new byte[digestSize];
        signer.digest.doFinal(hash, 0);
		Element h = signer.pairing.getG1().newElementFromHash(hash, 0, hash.length);
		BASPrivateKeyParameters privateKey = (BASPrivateKeyParameters) privKey;
    	Element sig = h.powZn(privateKey.getSk());
		return sig;
	}
	

	public Element aggreSign(Element currentSig, Element newSign) {
		// TODO Auto-generated method stub
		if(currentSig == null){
			return newSign;
		}
		return currentSig.mul(newSign);
	}


	public boolean verify(Server server) {
		// TODO Auto-generated method stub
		if(server == null){
			System.out.println("null pointer");
		}
		
		ArrayList<byte[]> bytesList = new ArrayList<byte[]>();
		int totalMessage = server.dataCollection.size();
		for(int i = 0 ; i < totalMessage ; i++ ){
			bytesList.add(server.dataCollection.get(i).getByte());
		}
		
		BASSigner[] signer = new BASSigner[totalMessage];
		
		for(int i = 0 ; i < totalMessage ; i++){
			Data tempData = server.dataCollection.get(i);
			
			BASPublicKeyParameters pubKey = (BASPublicKeyParameters) server.datamapCollection.get(tempData).getPubKey();
			signer[i] = new BASSigner(new SHA256Digest());
			signer[i].init(false,pubKey);
			signer[i].update(bytesList.get(i),0,bytesList.get(i).length);
		}
		// Generate the digest
		ArrayList<byte[]> hashes = new ArrayList<byte[]>();
		
		for(int i = 0 ; i < totalMessage ; i++ ){
			int digestSize = signer[i].digest.getDigestSize();
	        hashes.add( new byte[digestSize]);
	        signer[i].digest.doFinal(hashes.get(i), 0);
		}
        
		Element[] hs = new Element[totalMessage];
		for(int i = 0 ; i < totalMessage ; i++){
			hs[i] = signer[i].pairing.getG1().newElementFromHash(hashes.get(i), 0 , hashes.get(i).length);
		}
        Element[] temp2 = new Element[totalMessage];
        BASPublicKeyParameters publicKey = null;
        for(int i = 0 ; i < totalMessage ; i++ ){
        	publicKey = (BASPublicKeyParameters) signer[i].keyParameters;
        	temp2[i] = signer[i].pairing.pairing(hs[i], publicKey.getPk());
        }
        Element tempMul = temp2[0];
        for(int i = 1 ; i < totalMessage ; i++ ){
        	tempMul.mul(temp2[i]);
        }
        
		//byte[] currentSig = server.currentSig.toBytes();
        byte[] currentSig = null;
		try {
			currentSig = Base64.decode( server.currentSigString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Element sig = signer[0].pairing.getG1().newElementFromBytes(currentSig);
        Element temp1 = signer[0].pairing.pairing(sig, publicKey.getParameters().getG());
        return temp1.isEqual(tempMul);
	}
}
