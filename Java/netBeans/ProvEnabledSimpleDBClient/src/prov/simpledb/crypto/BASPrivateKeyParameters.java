package prov.simpledb.crypto;

import it.unisa.dia.gas.jpbc.Element;

public class BASPrivateKeyParameters extends BASKeyParameters {
    private Element sk;


    public BASPrivateKeyParameters(BASParameters parameters, Element sk) {
        super(true, parameters);
        this.sk = sk;
    }


    public Element getSk() {
        return sk;
    }
}