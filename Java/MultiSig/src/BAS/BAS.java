package BAS;

import java.util.HashMap;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.digests.SHA256Digest;

import engines.BASSigner;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import generators.BASKeyPairGenerator;
import generators.BASParametersGenerator;
import params.BASKeyGenerationParameters;
import params.BASParameters;
import params.BASPrivateKeyParameters;
import params.BASPublicKeyParameters;



public class BAS {
	boolean  isAggregate;
	int totalUser;
	int totalMessage;
	public BASParameters parameters;
	String propertiesFile;
	public AsymmetricCipherKeyPair[] keyPair = null; 
	public BAS(){
		isAggregate = false;
	}
	public BAS(int nUser, int nMessage) {
		totalUser = nUser;
		totalMessage = nMessage;
		isAggregate = true;
		Setup();
	}
	
	
	public void Setup(){
		BASParametersGenerator setup = new BASParametersGenerator();
		setup.init(PairingFactory.getPairingParameters("./resource/params.properties"));
		parameters = setup.generateParameters();
	}
	
	public AsymmetricCipherKeyPair keyGen(BASParameters parameters){
		BASKeyPairGenerator keyGen = new BASKeyPairGenerator();
		keyGen.init(new BASKeyGenerationParameters(null,parameters));
		return keyGen.generateKeyPair();
	}
	
	
	
	public byte[] sign(String message, CipherParameters privKey){
		byte[] bytes = message.getBytes();
		BASSigner signer = new BASSigner(new SHA256Digest());
		signer.init(true, privKey);
		signer.update(bytes,0,bytes.length);
		byte[] signature = null;
		try{
			signature = signer.generateSignature();
		} catch(CryptoException e){
			throw new RuntimeException(e);
		}
		return signature;
	}
	
	public byte[] aggreSign(String[] messages, CipherParameters[] privKeys, HashMap<Integer, Integer> hmap) {
		Element multiSig = null;
		try{
			byte[][] bytes = new byte[totalMessage][256];
			for(int i = 0 ; i < totalMessage ; i++ ){
				bytes[i] = messages[i].getBytes();
			}
			BASSigner[] signer = new BASSigner[totalMessage];
			
			for(int i = 0 ; i < totalMessage ; i++){
				int userIndex = hmap.get(i);
				signer[i] = new BASSigner(new SHA256Digest());
				signer[i].init(true,privKeys[userIndex]);
				signer[i].update(bytes[i],0,bytes[i].length);
			}
			// Generate the digest
			byte[][] hashes = new byte[totalMessage][256];
			
			for(int i = 0 ; i < totalMessage ; i++ ){
				int digestSize = signer[i].digest.getDigestSize();
		        hashes[i] = new byte[digestSize];
		        signer[i].digest.doFinal(hashes[i], 0);
			}
	        
	
	        // Map the hash of the message m to some element of G1
			Element[] h = new Element[totalMessage];
			Element[] sig = new Element[totalMessage];
			for(int i = 0 ; i < totalMessage ; i++){
				int userIndex = hmap.get(i);
				BASPrivateKeyParameters privateKey = (BASPrivateKeyParameters) privKeys[userIndex];
	        	h[i] = signer[i].pairing.getG1().newElementFromHash(hashes[i], 0, hashes[i].length);
	        	sig[i] = h[i].powZn(privateKey.getSk());
			}
			multiSig = sig[0];
			for(int i = 1; i < totalMessage; i++ ){
				multiSig.mul(sig[i]);
			}
			
			
		}catch (DataLengthException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return multiSig.toBytes();
	}
	
	public boolean verify(byte[] signature, String message,CipherParameters pubKey){
		byte[] bytes = message.getBytes();
		BASSigner signer = new BASSigner(new SHA256Digest());
		signer.init(false, pubKey);
		signer.update(bytes,0,bytes.length);
		return signer.verifySignature(signature);
	}
	
	public boolean aggreVerify(byte[] aggSignature, String[] messages, CipherParameters[] pubKeys, HashMap<Integer, Integer> hmap){
		byte[][] bytes = new byte[totalMessage][256];
		for(int i = 0 ; i < totalMessage ; i++ ){
			bytes[i] = messages[i].getBytes();
		}
		
		BASSigner[] signer = new BASSigner[totalMessage];
		
		for(int i = 0 ; i < totalMessage ; i++){
			int userIndex = hmap.get(i);
			signer[i] = new BASSigner(new SHA256Digest());
			signer[i].init(false,pubKeys[userIndex]);
			signer[i].update(bytes[i],0,bytes[i].length);
		}
		// Generate the digest
		byte[][] hashes = new byte[totalMessage][256];
		
		for(int i = 0 ; i < totalMessage ; i++ ){
			int digestSize = signer[i].digest.getDigestSize();
	        hashes[i] = new byte[digestSize];
	        signer[i].digest.doFinal(hashes[i], 0);
		}
        
		Element[] hs = new Element[totalMessage];
		for(int i = 0 ; i < totalMessage ; i++){
			hs[i] = signer[i].pairing.getG1().newElementFromHash(hashes[i], 0 , hashes[i].length);
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
        
		
        Element sig = signer[0].pairing.getG1().newElementFromBytes(aggSignature);
        Element temp1 = signer[0].pairing.pairing(sig, publicKey.getParameters().getG());
        
        //System.out.println(tempMul.toString());
        //System.out.println(temp1.toString());
        return temp1.isEqual(tempMul);
		
	}
}
