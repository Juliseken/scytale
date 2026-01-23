package de.juliseken.scytale.rsa.impl;

import de.juliseken.scytale.api.CipherText;
import de.juliseken.scytale.api.Message;
import de.juliseken.scytale.api.PublicKeyEncryption;
import de.juliseken.scytale.rsa.api.RSAPrivateKey;
import de.juliseken.scytale.rsa.api.RSAPublicKey;
import de.juliseken.scytale.text.CipherTextImpl;
import de.juliseken.scytale.text.MessageImpl;

public class RSAEncryption implements PublicKeyEncryption<RSAPrivateKey, RSAPublicKey> {
    public CipherText encrypt(Message message, RSAPublicKey key) {
        return new CipherTextImpl(RSAPrimitive.apply(message.getContent(), key));
    }

    public Message decrypt(CipherText cipherText, RSAPrivateKey key) {
        return new MessageImpl(RSAPrimitive.apply(cipherText.getContent(), key));
    }
}
