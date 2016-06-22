package prov.simpledb.crypto;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.bouncycastle.crypto.KeyGenerationParameters;



public class BASKeyPairGenerator implements AsymmetricCipherKeyPairGenerator {
    private BASKeyGenerationParameters param;


    public void init(KeyGenerationParameters param) {
        this.param = (BASKeyGenerationParameters) param;
    }

    public AsymmetricCipherKeyPair generateKeyPair() {
        BASParameters parameters = param.getParameters();

        Pairing pairing = PairingFactory.getPairing(parameters.getParameters());
        Element g = parameters.getG();

        // Generate the secret key
        Element sk = pairing.getZr().newRandomElement();

        // Generate the corresponding public key
        Element pk = g.powZn(sk);

        return new AsymmetricCipherKeyPair(
            new BASPublicKeyParameters(parameters, pk.getImmutable()),
            new BASPrivateKeyParameters(parameters, sk.getImmutable())
        );
    }
    
    public Keys generateKeyPair(String filename) {
        BASParameters parameters = param.getParameters();

        Pairing pairing = PairingFactory.getPairing(parameters.getParameters());
        Element g = parameters.getG();

        // Generate the secret key
        Element sk = pairing.getZr().newRandomElement();

        // Generate the corresponding public key
        Element pk = g.powZn(sk);
        
        return new Keys(pk,sk,parameters);
    }

}
