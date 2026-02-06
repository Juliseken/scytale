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

@Command(name = "decrypt")
public class RsaNaiveDecryptCommand implements Runnable {

    @Option(names = {"-i", "--in-file"}, required = true)
    Path input;

    @Option(names = {"-o", "--out-file"}, required = true)
    Path output;

    @Option(names = {"-k", "--key-file"}, required = false)
    Path privateKeyFile;

    @Override
    public void run() {
        if (privateKeyFile == null) {
            privateKeyFile = Paths.get(System.getProperty("user.home"))
                .resolve(".ssh").resolve("rsa-naive");
        }

        RSAKeyReader keyReader = new RSAKeyReader();
        BlockInput blockInput = new BlockInput();
        BlockOutput blockOutput = new BlockOutput();
        PublicKeyEncryption<RSAPrivateKey, RSAPublicKey> rsaEncryption = new RSAEncryption();

        try {
            RSAPrivateKey privateKey = keyReader.readPrivate(privateKeyFile);
            int keyBitLength = privateKey.getBitLength();
            int cipherTextBlockSize = keyBitLength % 8 == 0 ? keyBitLength / 8 : keyBitLength / 8 + 1;
            int messageBlockSize = cipherTextBlockSize - 1;

            CipherTextBlockCodec cipherTextCodec = new CipherTextBlockCodec(cipherTextBlockSize);
            MessageBlockCodec messageCodec = new MessageBlockCodec(messageBlockSize);

            List<byte[]> cipherBlocks = blockInput.readBlocks(input, cipherTextBlockSize, false);
            List<CipherText> cipherTexts = cipherBlocks.stream()
                .map(cipherTextCodec::fromBytes)
                .toList();

            List<Message> messages = cipherTexts.stream()
                .map(m -> rsaEncryption.decrypt(m, privateKey))
                .toList();
            
            List<byte[]> outBlocks = messages.stream()
                .map(messageCodec::toBytes)
                .toList();
            blockOutput.writeBlocks(outBlocks, output, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
