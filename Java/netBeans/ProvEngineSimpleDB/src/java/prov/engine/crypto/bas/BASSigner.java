package prov.engine.crypto.bas;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.Signer;



public class BASSigner implements Signer {
    public BASKeyParameters keyParameters;
    public Digest digest;

    public Pairing pairing;


    public BASSigner(Digest digest) {
        this.digest = digest;
    }

    
    public void init(boolean forSigning, CipherParameters param) {
        if (!(param instanceof BASKeyParameters))
            throw new IllegalArgumentException("Invalid parameters. Expected an instance of BLS01KeyParameters.");

        keyParameters = (BASKeyParameters) param;

        if (forSigning && !keyParameters.isPrivate())
            throw new IllegalArgumentException("signing requires private key");
        if (!forSigning && keyParameters.isPrivate())
            throw new IllegalArgumentException("verification requires public key");

        this.pairing = PairingFactory.getPairing(keyParameters.getParameters().getParameters());

        // Reset the digest
        digest.reset();
    }

    public boolean verifySignature(byte[] signature) {
        if (keyParameters == null)
            throw new IllegalStateException("BLS engine not initialised");

        BASPublicKeyParameters publicKey = (BASPublicKeyParameters) keyParameters;

        Element sig = pairing.getG1().newElementFromBytes(signature);

        // Generate the digest
        int digestSize = digest.getDigestSize();
        byte[] hash = new byte[digestSize];
        digest.doFinal(hash, 0);

        // Map the hash of the message m to some element of G1
        Element h = pairing.getG1().newElementFromHash(hash, 0, hash.length);

        Element temp1 = pairing.pairing(sig, publicKey.getParameters().getG());
        Element temp2 = pairing.pairing(h, publicKey.getPk());

        return temp1.isEqual(temp2);
    }

    
    
    public byte[] generateSignature() throws CryptoException, DataLengthException {
        if (keyParameters == null)
            throw new IllegalStateException("BAS engine not initialised");

        BASPrivateKeyParameters privateKey = (BASPrivateKeyParameters) keyParameters;

        // Generate the digest
        int digestSize = digest.getDigestSize();
        byte[] hash = new byte[digestSize];
        digest.doFinal(hash, 0);

        // Map the hash of the message m to some element of G1
        Element h = pairing.getG1().newElementFromHash(hash, 0, hash.length);

        // Generate the signature
        Element sig = h.powZn(privateKey.getSk());
        
        return sig.toBytes();
    }

    public void reset() {
        digest.reset();
    }

    public void update(byte b) {
        digest.update(b);
    }

    public void update(byte[] in, int off, int len) {
        digest.update(in, off, len);
    }

}
