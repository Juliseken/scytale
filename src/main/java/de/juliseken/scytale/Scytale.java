package de.juliseken.scytale;

import java.io.IOException;
import java.nio.file.Path;

import de.juliseken.scytale.key.api.PublicKeyEncryption;
import de.juliseken.scytale.rsa.api.RSAPrivateKey;
import de.juliseken.scytale.rsa.api.RSAPublicKey;
import de.juliseken.scytale.rsa.impl.RSAEncryption;
import de.juliseken.scytale.rsa.impl.RSAKeyPair;
import de.juliseken.scytale.rsa.io.RSAKeyReader;
import de.juliseken.scytale.text.api.CipherText;
import de.juliseken.scytale.text.api.Message;
import de.juliseken.scytale.text.io.TextReader;
import de.juliseken.scytale.text.io.TextWriter;

public class Scytale {
    public static void main(String[] args) {
        try {
            System.out.println("Hello Scytale!");

            RSAKeyReader keyReader = new RSAKeyReader();
            RSAPrivateKey privateKey = keyReader.readPrivate("rsa-naive");
            RSAPublicKey publicKey = keyReader.readPublic("rsa-naive.pub");
            
            RSAKeyPair keyPairFromFile = new RSAKeyPair(privateKey, publicKey);

            TextReader textReader = new TextReader();

            Message message = textReader.readMessage(Path.of("message.txt"));

            System.out.println("Original message: " + message);

            PublicKeyEncryption<RSAPrivateKey, RSAPublicKey> rsaEncryption = new RSAEncryption();
            CipherText cipherText = rsaEncryption.encrypt(message, keyPairFromFile.getPublicKey());

            System.out.println("Encrypted message: " + cipherText);

            TextWriter textWriter = new TextWriter();
            textWriter.write(cipherText, Path.of("ciphertext.txt"));

            Message decryptedMessage = rsaEncryption.decrypt(cipherText, keyPairFromFile.getPrivateKey());
            System.out.println("Decrypted message: " + decryptedMessage);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
