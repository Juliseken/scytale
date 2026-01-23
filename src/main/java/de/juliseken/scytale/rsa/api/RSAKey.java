package de.juliseken.scytale.rsa.api;

import java.math.BigInteger;

import de.juliseken.scytale.key.api.Key;

public interface RSAKey extends Key {
    public BigInteger getExponent();
    public BigInteger getModulus();
}
