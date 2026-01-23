package de.juliseken.scytale.rsa.impl;

import java.math.BigInteger;

import de.juliseken.scytale.rsa.api.RSAKey;

public abstract class AbstractRSAKey implements RSAKey {
    private BigInteger exponent;
    private BigInteger modulus;

    public AbstractRSAKey(BigInteger exponent, BigInteger modulus) {
        this.exponent = exponent;
        this.modulus = modulus;
    }

    public BigInteger getExponent() {
        return exponent;
    }

    public BigInteger getModulus() {
        return modulus;
    }
}
