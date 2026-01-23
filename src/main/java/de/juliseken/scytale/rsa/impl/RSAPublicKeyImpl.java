package de.juliseken.scytale.rsa.impl;

import java.math.BigInteger;

import de.juliseken.scytale.rsa.api.RSAPublicKey;

public class RSAPublicKeyImpl extends AbstractRSAKey implements RSAPublicKey {

    public RSAPublicKeyImpl(BigInteger exponent, BigInteger modulus) {
        super(exponent, modulus);
    }
}
