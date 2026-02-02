package de.juliseken.scytale.integration;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import de.juliseken.scytale.key.api.PublicKeyEncryption;
import de.juliseken.scytale.math.impl.NumberTheoryNaive;
import de.juliseken.scytale.rsa.api.RSAPrivateKey;
import de.juliseken.scytale.rsa.api.RSAPublicKey;
import de.juliseken.scytale.rsa.impl.RSAEncryption;
import de.juliseken.scytale.rsa.impl.RSAKeyPair;
import de.juliseken.scytale.rsa.impl.RSAKeyPairGenerator;
import de.juliseken.scytale.text.api.CipherText;
import de.juliseken.scytale.text.api.Message;
import de.juliseken.scytale.text.codec.CipherTextBlockCodec;
import de.juliseken.scytale.text.codec.MessageBlockCodec;
import de.juliseken.scytale.text.io.BlockInput;
import de.juliseken.scytale.text.io.BlockOutput;

public class RSARoundTripTest {
    
    @Test
    public void roundTrip(@TempDir Path tempDir) throws IOException {
        // Arrange
        int keyBitLength = 32;
        int cipherTextBlockSize = keyBitLength / 8;
        int messageBlockSize = keyBitLength / 8 - 1;

        RSAKeyPairGenerator keyGen = new RSAKeyPairGenerator();
        
        String plaintext = "Hello RSA roundtrip!!";
        Path plainPath = tempDir.resolve("plain.txt");
        Path cipherPath = tempDir.resolve("cipher.txt");
        Path decryptedPath = tempDir.resolve("decrypted.txt");

        BlockInput blockInput = new BlockInput();
        BlockOutput blockOutput = new BlockOutput();

        MessageBlockCodec messageCodec = new MessageBlockCodec(messageBlockSize);
        CipherTextBlockCodec cipherTextCodec = new CipherTextBlockCodec(cipherTextBlockSize);

        PublicKeyEncryption<RSAPrivateKey, RSAPublicKey> rsaEncryption = new RSAEncryption();

        // Act
        RSAKeyPair keyPair = keyGen.generate(new NumberTheoryNaive(), keyBitLength);
        RSAPublicKey pub = keyPair.getPublicKey();
        RSAPrivateKey priv = keyPair.getPrivateKey();

        Files.writeString(plainPath, plaintext, StandardCharsets.UTF_8);

        List<byte[]> messageBlocks = blockInput.readBlocks(plainPath, messageBlockSize, true);
        List<Message> messages = messageBlocks.stream()
            .map(messageCodec::fromBytes)
            .toList();

        List<CipherText> cipherTexts = messages.stream()
            .map(m -> rsaEncryption.encrypt(m, pub))
            .toList();
        
        List<byte[]> cipherBlocks = cipherTexts.stream()
            .map(cipherTextCodec::toBytes)
            .toList();

        blockOutput.writeBlocks(cipherBlocks, cipherPath, false);

        List<byte[]> cipherBlocksRead = blockInput.readBlocks(cipherPath, cipherTextBlockSize, false);
        
        List<CipherText> cipherTextsRead = cipherBlocksRead.stream()
            .map(cipherTextCodec::fromBytes)
            .toList();
        
        List<Message> messagesRead = cipherTextsRead.stream()
            .map(c -> rsaEncryption.decrypt(c, priv))
            .toList();
        
        List<byte[]> messageBlocksRead = messagesRead.stream()
            .map(messageCodec::toBytes)
            .toList();
        
        blockOutput.writeBlocks(messageBlocksRead, decryptedPath, true);

        byte[] original = Files.readAllBytes(plainPath);
        byte[] decrypted = Files.readAllBytes(decryptedPath);

        // Assert
        assertArrayEquals(original, decrypted);
    }
}
