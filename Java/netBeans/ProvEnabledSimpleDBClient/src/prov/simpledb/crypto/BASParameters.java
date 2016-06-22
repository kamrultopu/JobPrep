package prov.simpledb.crypto;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.PairingParameters;

import org.bouncycastle.crypto.CipherParameters;

public class BASParameters implements CipherParameters {
	private PairingParameters parameters;
    private Element g;


    public BASParameters(PairingParameters parameters, Element g) {
        this.parameters = parameters;
        this.g = g.getImmutable();
    }


    public PairingParameters getParameters() {
        return parameters;
    }

    public Element getG() {
        return g;
    }

}
