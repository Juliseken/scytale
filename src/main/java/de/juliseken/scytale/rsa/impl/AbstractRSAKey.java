package de.juliseken.scytale.rsa.impl;

import java.math.BigInteger;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RSAKey other = (RSAKey) o;
        return exponent.equals(other.getExponent()) &&
               modulus.equals(other.getModulus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(exponent, modulus);
    }
}
