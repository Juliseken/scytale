package de.juliseken.scytale.rsa;

import de.juliseken.scytale.api.CipherText;
import de.juliseken.scytale.api.Message;
import de.juliseken.scytale.api.PublicKeyEncryption;
import de.juliseken.scytale.text.CipherTextImpl;
import de.juliseken.scytale.text.MessageImpl;

public class Encryption implements PublicKeyEncryption<PrivateKey, PublicKey> {
    public CipherText encrypt(Message message, PublicKey key) {
        return new CipherTextImpl(RSAPrimitive.apply(message.getContent(), key));
    }

    public Message decrypt(CipherText cipherText, PrivateKey key) {
        return new MessageImpl(RSAPrimitive.apply(cipherText.getContent(), key));
    }
}
