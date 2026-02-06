package de.juliseken.scytale.cli.rsa.naive;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import de.juliseken.scytale.key.api.PublicKeyEncryption;
import de.juliseken.scytale.rsa.api.RSAPrivateKey;
import de.juliseken.scytale.rsa.api.RSAPublicKey;
import de.juliseken.scytale.rsa.impl.RSAEncryption;
import de.juliseken.scytale.rsa.io.RSAKeyReader;
import de.juliseken.scytale.text.api.CipherText;
import de.juliseken.scytale.text.api.Message;
import de.juliseken.scytale.text.codec.CipherTextBlockCodec;
import de.juliseken.scytale.text.codec.MessageBlockCodec;
import de.juliseken.scytale.text.io.BlockInput;
import de.juliseken.scytale.text.io.BlockOutput;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "encrypt")
public class RsaNaiveEncryptCommand implements Runnable {

    @Option(names = {"-i", "--in-file"}, required = true)
    Path input;

    @Option(names = {"-o", "--out-file"}, required = true)
    Path output;

    @Option(names = {"-k", "--key-file"}, required = false)
    Path publicKeyFile;

    public void run() {
        if (publicKeyFile == null) {
            publicKeyFile = Paths.get(System.getProperty("user.home"))
                .resolve(".ssh").resolve("rsa-naive.pub");
        }

        RSAKeyReader keyReader = new RSAKeyReader();
        BlockInput blockInput = new BlockInput();
        BlockOutput blockOutput = new BlockOutput();
        PublicKeyEncryption<RSAPrivateKey, RSAPublicKey> rsaEncryption = new RSAEncryption();

        try {
            RSAPublicKey publicKey = keyReader.readPublic(publicKeyFile);
            int keyBitLength = publicKey.getBitLength();
            int cipherTextBlockSize = keyBitLength / 2;
            int messageBlockSize = cipherTextBlockSize - 1;

            MessageBlockCodec messageCodec = new MessageBlockCodec(messageBlockSize);
            CipherTextBlockCodec cipherTextCodec = new CipherTextBlockCodec(cipherTextBlockSize);

            List<byte[]> messageBlocks = blockInput.readBlocks(input, messageBlockSize, true);
            List<Message> messages = messageBlocks.stream()
                .map(messageCodec::fromBytes)
                .toList();

            List<CipherText> cipherTexts = messages.stream()
                .map(m -> rsaEncryption.encrypt(m, publicKey))
                .toList();
            
            List<byte[]> outBlocks = cipherTexts.stream()
                .map(cipherTextCodec::toBytes)
                .toList();
            blockOutput.writeBlocks(outBlocks, output, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
