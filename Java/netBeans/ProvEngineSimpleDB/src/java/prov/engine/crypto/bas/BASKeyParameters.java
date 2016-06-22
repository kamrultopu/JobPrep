package prov.engine.crypto.bas;

import org.bouncycastle.crypto.params.AsymmetricKeyParameter;

public class BASKeyParameters extends AsymmetricKeyParameter {
    private BASParameters parameters;


    public BASKeyParameters(boolean isPrivate, BASParameters parameters) {
        super(isPrivate);
        this.parameters = parameters;
    }


    public BASParameters getParameters() {
        return parameters;
    }

}

