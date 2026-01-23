package de.juliseken.scytale.text;

import java.math.BigInteger;

public class CipherTextImpl extends AbstractText implements de.juliseken.scytale.api.CipherText {
    public CipherTextImpl(BigInteger content) {
        super(content);
    }
}
