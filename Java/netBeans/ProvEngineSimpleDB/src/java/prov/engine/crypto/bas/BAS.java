package prov.engine.crypto.bas;

import java.util.ArrayList;
import java.util.HashMap;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.digests.SHA256Digest;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import prov.engine.util.Config;

public class BAS {
	//boolean  isAggregate;
    //int totalUser;
    //int totalMessage;
    //String propertiesFile;

    public BASParameters parameters;
	//public AsymmetricCipherKeyPair[] keyPair = null; 

    public BAS() {
		//totalUser = nUser;
        //totalMessage = nMessage;
        //isAggregate = true;
        Setup();
    }

    public void Setup() {
        BASParametersGenerator setup = new BASParametersGenerator();
        System.out.println("setup = " + Config.Instanc().BASPrpertyFile());
        setup.init(PairingFactory.getPairingParameters(Config.Instanc().BASPrpertyFile()));
        parameters = setup.generateParameters();
    }

    public AsymmetricCipherKeyPair keyGen() {
        BASKeyPairGenerator keyGen = new BASKeyPairGenerator();
        keyGen.init(new BASKeyGenerationParameters(null, parameters));
        return keyGen.generateKeyPair();
    }

    public Element sign(String currentData, CipherParameters privKey) {
        byte[] bytes = currentData.getBytes();
        BASSigner signer = new BASSigner(new SHA256Digest());
        signer.init(true, privKey);
        signer.update(bytes, 0, bytes.length);
        int digestSize = signer.digest.getDigestSize();
        byte[] hash = new byte[digestSize];
        signer.digest.doFinal(hash, 0);
        Element h = signer.pairing.getG1().newElementFromHash(hash, 0, hash.length);
        BASPrivateKeyParameters privateKey = (BASPrivateKeyParameters) privKey;
        Element sig = h.powZn(privateKey.getSk());
        /*byte[] signature = null;
         try{
         signature = signer.generateSignature();
         } catch(CryptoException e){
         throw new RuntimeException(e);
         }*/
        return sig;
    }

    public Element aggreSign(Element currentSig, Element newSign) {
        // TODO Auto-generated method stub
        if (currentSig == null) {
            return newSign;
        }
        return currentSig.mul(newSign);
    }

}
