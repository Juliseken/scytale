package de.juliseken.scytale.text.codec;

import java.math.BigInteger;

import de.juliseken.scytale.text.api.CipherText;
import de.juliseken.scytale.text.impl.CipherTextImpl;

public class CipherTextBlockCodec {

    private final int blockSize;

    public CipherTextBlockCodec(int blockSize) {
        this.blockSize = blockSize;
    }

    public CipherText fromBytes(byte[] block) {
        return new CipherTextImpl(new BigInteger(1, block));
    }

    public byte[] toBytes(CipherText cipherText) {
        return normalize(cipherText.getContent().toByteArray());
    }

    private byte[] normalize(byte[] raw) {
        byte[] normalized = new byte[blockSize];
        int start = raw.length > blockSize ? 1 : 0;
        System.arraycopy(raw, start,
            normalized, blockSize - raw.length + start, Math.min(blockSize, raw.length));
        return normalized;
    }
}
