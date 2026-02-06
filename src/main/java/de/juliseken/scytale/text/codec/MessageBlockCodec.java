package de.juliseken.scytale.text.codec;

import java.math.BigInteger;

import de.juliseken.scytale.text.api.Message;
import de.juliseken.scytale.text.impl.MessageImpl;

public class MessageBlockCodec {

    private final int blockSize;

    public MessageBlockCodec(int blockSize) {
        this.blockSize = blockSize;
    }

    public Message fromBytes(byte[] block) {
        return new MessageImpl(new BigInteger(1, block));
    }

    public byte[] toBytes(Message message) {
        byte[] raw = message.getContent().toByteArray();
        return normalize(raw);
    }

    private byte[] normalize(byte[] raw) {
        byte[] normalized = new byte[blockSize];
        int start = raw.length > blockSize ? 1 : 0;
        System.arraycopy(raw, start,
            normalized, blockSize - raw.length + start, Math.min(blockSize, raw.length));
        return normalized;
    }
}
