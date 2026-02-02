package de.juliseken.scytale;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import de.juliseken.scytale.key.api.PublicKeyEncryption;
import de.juliseken.scytale.math.impl.NumberTheoryNaive;
import de.juliseken.scytale.rsa.api.RSAPrivateKey;
import de.juliseken.scytale.rsa.api.RSAPublicKey;
import de.juliseken.scytale.rsa.impl.RSAEncryption;
import de.juliseken.scytale.rsa.impl.RSAKeyPair;
import de.juliseken.scytale.rsa.impl.RSAKeyPairGenerator;
import de.juliseken.scytale.rsa.io.RSAKeyReader;
import de.juliseken.scytale.rsa.io.RSAKeyWriter;
import de.juliseken.scytale.text.api.CipherText;
import de.juliseken.scytale.text.api.Message;
import de.juliseken.scytale.text.codec.CipherTextBlockCodec;
import de.juliseken.scytale.text.codec.MessageBlockCodec;
import de.juliseken.scytale.text.io.BlockInput;
import de.juliseken.scytale.text.io.BlockOutput;

public class Scytale {

    private static int KEY_BIT_LENGTH = 32;
    private static int CIPHER_TEXT_BLOCK_SIZE = KEY_BIT_LENGTH / 8;
    private static int MESSAGE_BLOCK_SIZE = CIPHER_TEXT_BLOCK_SIZE - 1;

    private static void encryptFile() throws IOException {
        int cipherTextBlockSize = CIPHER_TEXT_BLOCK_SIZE;
        int messageBlockSize = MESSAGE_BLOCK_SIZE;

        RSAKeyReader keyReader = new RSAKeyReader();
        MessageBlockCodec messageCodec = new MessageBlockCodec(messageBlockSize);
        CipherTextBlockCodec cipherTextCodec = new CipherTextBlockCodec(cipherTextBlockSize);
        BlockInput blockInput = new BlockInput();
        BlockOutput blockOutput = new BlockOutput();
        PublicKeyEncryption<RSAPrivateKey, RSAPublicKey> rsaEncryption = new RSAEncryption();

        RSAPublicKey publicKey = keyReader.readPublic("rsa-naive.pub");

        List<byte[]> messageBlocks = blockInput.readBlocks(Path.of("message.txt"), messageBlockSize, true);
        List<Message> messages = messageBlocks.stream()
            .map(messageCodec::fromBytes)
            .toList();

        List<CipherText> cipherTexts = messages.stream()
            .map(m -> rsaEncryption.encrypt(m, publicKey))
            .toList();
        
        List<byte[]> outBlocks = cipherTexts.stream()
            .map(cipherTextCodec::toBytes)
            .toList();
        blockOutput.writeBlocks(outBlocks, Path.of("ciphertext"), false);
    }

    private static void decryptFile() throws IOException {
        int cipherTextBlockSize = CIPHER_TEXT_BLOCK_SIZE;
        int messageBlockSize = MESSAGE_BLOCK_SIZE;

        RSAKeyReader keyReader = new RSAKeyReader();
        MessageBlockCodec messageCodec = new MessageBlockCodec(messageBlockSize);
        CipherTextBlockCodec cipherTextCodec = new CipherTextBlockCodec(cipherTextBlockSize);
        BlockInput blockInput = new BlockInput();
        BlockOutput blockOutput = new BlockOutput();
        PublicKeyEncryption<RSAPrivateKey, RSAPublicKey> rsaEncryption = new RSAEncryption();

        RSAPrivateKey privateKey = keyReader.readPrivate("rsa-naive");

        List<byte[]> cipherBlocks = blockInput.readBlocks(Path.of("ciphertext"), cipherTextBlockSize, false);
        List<CipherText> cipherTexts = cipherBlocks.stream()
            .map(cipherTextCodec::fromBytes)
            .toList();

        List<Message> messages = cipherTexts.stream()
            .map(m -> rsaEncryption.decrypt(m, privateKey))
            .toList();
        
        List<byte[]> outBlocks = messages.stream()
            .map(messageCodec::toBytes)
            .toList();
        blockOutput.writeBlocks(outBlocks, Path.of("decrypted.txt"), true);
    }

    private static void generateKeyPair() throws IOException {
        RSAKeyPairGenerator generator = new RSAKeyPairGenerator();
        RSAKeyWriter w = new RSAKeyWriter();

        RSAKeyPair keyPair = generator.generate(new NumberTheoryNaive(), KEY_BIT_LENGTH);
        w.write(keyPair.getPrivateKey(), "rsa-naive");
        w.write(keyPair.getPublicKey(), "rsa-naive.pub");
    }

    public static void main(String[] args) {
        try {
            encryptFile();
            decryptFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
