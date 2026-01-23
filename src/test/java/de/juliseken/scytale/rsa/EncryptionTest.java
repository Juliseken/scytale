package de.juliseken.scytale.rsa;

import org.junit.jupiter.api.Test;
import java.math.BigInteger;
import static org.junit.jupiter.api.Assertions.assertEquals;

import de.juliseken.scytale.api.CipherText;
import de.juliseken.scytale.api.Message;
import de.juliseken.scytale.api.PublicKeyEncryption;
import de.juliseken.scytale.rsa.api.RSAPrivateKey;
import de.juliseken.scytale.rsa.api.RSAPublicKey;
import de.juliseken.scytale.rsa.impl.RSAEncryption;
import de.juliseken.scytale.rsa.impl.RSAKeyPair;
import de.juliseken.scytale.rsa.impl.RSAKeyPairGenerator;
import de.juliseken.scytale.text.CipherTextImpl;
import de.juliseken.scytale.text.MessageImpl;

public class EncryptionTest {
    
    @Test
    public void testEncrypt() {
        RSAKeyPair keyPair = new RSAKeyPairGenerator().generate();
        Message message = new MessageImpl(new BigInteger("88"));

        PublicKeyEncryption<RSAPrivateKey, RSAPublicKey> encryption = new RSAEncryption();
        CipherText cipherText = encryption.encrypt(message, keyPair.getPublicKey());

        assertEquals(new CipherTextImpl(new BigInteger("11")), cipherText);

        Message decryptedMessage = encryption.decrypt(cipherText, keyPair.getPrivateKey());
        assertEquals(message, decryptedMessage);
    }
}
