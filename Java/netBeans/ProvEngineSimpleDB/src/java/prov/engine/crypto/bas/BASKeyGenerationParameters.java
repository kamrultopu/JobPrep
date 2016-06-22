package prov.engine.crypto.bas;

import java.security.SecureRandom;

import org.bouncycastle.crypto.KeyGenerationParameters;

public class BASKeyGenerationParameters extends KeyGenerationParameters {

    private BASParameters params;

    public BASKeyGenerationParameters(SecureRandom random, BASParameters params) {
        super(random, params.getG().getField().getLengthInBytes());

        this.params = params;
    }

    public BASParameters getParameters() {
        return params;
    }

}
