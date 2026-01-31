package de.juliseken.scytale.text.io;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;

import de.juliseken.scytale.text.api.CipherText;
import de.juliseken.scytale.text.api.Message;
import de.juliseken.scytale.text.impl.CipherTextImpl;
import de.juliseken.scytale.text.impl.MessageImpl;

public class TextReader {

    public Message readMessage(Path path) throws IOException {
        String raw = Files.readString(path);
        return new MessageImpl(new BigInteger(raw));
    }

    public CipherText readCipherText(Path path) throws IOException {
        String raw = Files.readString(path);
        return new CipherTextImpl(new BigInteger(raw));
    }
}
