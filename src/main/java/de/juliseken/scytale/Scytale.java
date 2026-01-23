package de.juliseken.scytale;

import java.math.BigInteger;

import de.juliseken.scytale.api.CipherText;
import de.juliseken.scytale.api.Message;
import de.juliseken.scytale.api.PublicKeyEncryption;
import de.juliseken.scytale.rsa.api.RSAPrivateKey;
import de.juliseken.scytale.rsa.api.RSAPublicKey;
import de.juliseken.scytale.rsa.impl.RSAEncryption;
import de.juliseken.scytale.rsa.impl.RSAKeyPair;
import de.juliseken.scytale.rsa.impl.RSAKeyPairGenerator;
import de.juliseken.scytale.text.MessageImpl;

public class Scytale {
    public static void main(String[] args) {
        System.out.println("Hello Scytale!");

        RSAKeyPair keyPair = new RSAKeyPairGenerator().generate();
        Message message = new MessageImpl(new BigInteger("88"));

        System.out.println("Original message: " + message);

        PublicKeyEncryption<RSAPrivateKey, RSAPublicKey> rsaEncryption = new RSAEncryption();
        CipherText cipherText = rsaEncryption.encrypt(message, keyPair.getPublicKey());

        System.out.println("Encrypted message: " + cipherText);

        Message decryptedMessage = rsaEncryption.decrypt(cipherText, keyPair.getPrivateKey());
        System.out.println("Decrypted message: " + decryptedMessage);
    }
}
