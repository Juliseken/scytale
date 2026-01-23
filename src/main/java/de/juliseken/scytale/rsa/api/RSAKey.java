package de.juliseken.scytale.rsa.api;

import java.math.BigInteger;

public interface RSAKey extends de.juliseken.scytale.api.Key {
    public BigInteger getExponent();
    public BigInteger getModulus();
}
