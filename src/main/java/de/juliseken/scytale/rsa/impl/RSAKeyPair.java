package de.juliseken.scytale.rsa.impl;

import de.juliseken.scytale.key.api.KeyPair;
import de.juliseken.scytale.rsa.api.RSAPrivateKey;
import de.juliseken.scytale.rsa.api.RSAPublicKey;

public class RSAKeyPair implements KeyPair {

    private RSAPrivateKey privateKey;
    private RSAPublicKey publicKey;

    public RSAKeyPair(RSAPrivateKey privateKey, RSAPublicKey publicKey) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    public RSAPublicKey getPublicKey() {
        return publicKey;
    }

    public RSAPrivateKey getPrivateKey() {
        return privateKey;
    }
}
