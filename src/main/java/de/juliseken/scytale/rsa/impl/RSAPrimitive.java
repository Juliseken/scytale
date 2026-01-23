package de.juliseken.scytale.rsa.impl;

import java.math.BigInteger;

import de.juliseken.scytale.rsa.api.RSAKey;

final class RSAPrimitive {
    public static BigInteger apply(BigInteger input, RSAKey key) {
        return input.modPow(key.getExponent(), key.getModulus());
    }
}
