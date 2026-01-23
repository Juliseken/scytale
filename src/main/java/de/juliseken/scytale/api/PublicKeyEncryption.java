package de.juliseken.scytale.api;

public interface PublicKeyEncryption<PRIV extends PrivateKey, PUB extends PublicKey> {
    public CipherText encrypt(Message message, PUB key);
    public Message decrypt(CipherText cipherText, PRIV key);
}
