package params;

import it.unisa.dia.gas.jpbc.Element;

public class BASPublicKeyParameters extends BASKeyParameters {
    private Element pk;


    public BASPublicKeyParameters(BASParameters parameters, Element pk) {
        super(false, parameters);
        this.pk = pk;
    }


    public Element getPk() {
        return pk;
    }
}
