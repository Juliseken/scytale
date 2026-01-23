package de.juliseken.scytale.rsa.impl;

import java.math.BigInteger;

import de.juliseken.scytale.rsa.api.RSAPrivateKey;
import de.juliseken.scytale.rsa.api.RSAPublicKey;

public class RSAKeyPairGenerator implements de.juliseken.scytale.api.KeyPairGenerator {
    public RSAKeyPair generate() {
        BigInteger p = new BigInteger("17");
        BigInteger q = new BigInteger("11");
        BigInteger n = p.multiply(q);
        //BigInteger phi_n = p.min(BigInteger.ONE).multiply(q.min(BigInteger.ONE));
        BigInteger e = new BigInteger("7");
        BigInteger d = new BigInteger("23");
        RSAPrivateKey privateKey = new RSAPrivateKeyImpl(e, n);
        RSAPublicKey publicKey = new RSAPublicKeyImpl(d, n);
        return new RSAKeyPair(privateKey, publicKey);
    }
}
