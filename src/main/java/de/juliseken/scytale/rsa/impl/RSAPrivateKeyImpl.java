package de.juliseken.scytale.rsa.impl;

import java.math.BigInteger;

import de.juliseken.scytale.rsa.api.RSAPrivateKey;

public class RSAPrivateKeyImpl extends AbstractRSAKey implements RSAPrivateKey {

    public RSAPrivateKeyImpl(BigInteger exponent, BigInteger modulus) {
        super(exponent, modulus);
    }
}
