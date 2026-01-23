package de.juliseken.scytale;

import java.math.BigInteger;

import de.juliseken.scytale.api.CipherText;
import de.juliseken.scytale.api.Message;
import de.juliseken.scytale.api.PublicKeyEncryption;
import de.juliseken.scytale.rsa.PrivateKey;
import de.juliseken.scytale.rsa.PublicKey;
import de.juliseken.scytale.text.MessageImpl;
import de.juliseken.scytale.rsa.KeyPair;
import de.juliseken.scytale.rsa.KeyPairGenerator;
import de.juliseken.scytale.rsa.Encryption;

public class Scytale {
    public static void main(String[] args) {
        System.out.println("Hello Scytale!");

        KeyPair keyPair = new KeyPairGenerator().generate();
        Message message = new MessageImpl(new BigInteger("88"));

        System.out.println("Original message: " + message);

        PublicKeyEncryption<PrivateKey, PublicKey> rsaEncryption = new Encryption();
        CipherText cipherText = rsaEncryption.encrypt(message, keyPair.getPublicKey());

        System.out.println("Encrypted message: " + cipherText);

        Message decryptedMessage = rsaEncryption.decrypt(cipherText, keyPair.getPrivateKey());
        System.out.println("Decrypted message: " + decryptedMessage);
    }
}
