package prov.simpledb.crypto;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.jpbc.PairingParameters;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

public class BASParametersGenerator {

	private PairingParameters parameters;
    private Pairing pairing;


    public void init(PairingParameters parameters) {
        this.parameters = parameters;
        this.pairing = PairingFactory.getPairing(parameters);
    }

    public BASParameters generateParameters() {
        Element g = pairing.getG2().newRandomElement();

        return new BASParameters(parameters, g.getImmutable());
    }
}
