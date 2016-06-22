package generators;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.bouncycastle.crypto.KeyGenerationParameters;

import params.BASKeyGenerationParameters;
import params.BASParameters;
import params.Keys;

public class BASKeyPairGenerator implements AsymmetricCipherKeyPairGenerator {
    private BASKeyGenerationParameters param;


    public void init(KeyGenerationParameters param) {
        this.param = (BASKeyGenerationParameters) param;
    }

    public Keys generateKeyPair(String filename) {
        BASParameters parameters = param.getParameters();

        Pairing pairing = PairingFactory.getPairing(parameters.getParameters());
        Element g = parameters.getG();

        // Generate the secret key
        Element sk = pairing.getZr().newRandomElement();

        // Generate the corresponding public key
        Element pk = g.powZn(sk);
        //byte[] bytesPK = pk.toBytes();
        //byte[] bytesSK = sk.toBytes();
        /*String path = "./resource/pub_" +filename;
        FileOutputStream fos;
		try {
			fos = new FileOutputStream(path);
			fos.write(bytesPK);
	        fos.close();
	        path = "./resource/priv_" + filename;
	        fos = new FileOutputStream(path);
			fos.write(bytesPK);
	        fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
        
        
        
        //Element[] elements = {pk,sk};
        return new Keys(pk,sk,parameters);

   
        /*return new AsymmetricCipherKeyPair(
            new BASPublicKeyParameters(parameters, pk.getImmutable()),
            new BASPrivateKeyParameters(parameters, sk.getImmutable())
        );*/
    }

	@Override
	public AsymmetricCipherKeyPair generateKeyPair() {
		// TODO Auto-generated method stub
		return null;
	}

}
