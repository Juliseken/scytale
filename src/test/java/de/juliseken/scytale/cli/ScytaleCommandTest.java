package de.juliseken.scytale.cli;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import picocli.CommandLine;

public class ScytaleCommandTest {
    
    @Test
    public void testCli(@TempDir Path tempDir) throws IOException {
        String plaintext = "Hello RSA roundtrip!!";
        Path plainPath = tempDir.resolve("plain.txt");
        Path cipherPath = tempDir.resolve("cipher.txt");
        Path decryptedPath = tempDir.resolve("decrypted.txt");
        Path publicKeyPath = tempDir.resolve("rsa-naive.pub");
        Path privateKeyPath = tempDir.resolve("rsa-naive");

        Files.writeString(plainPath, plaintext, StandardCharsets.UTF_8);

        String[] args1 = {"rsa-naive", "keygen", "-o", tempDir.toString()};
        int exitCode = new CommandLine(new ScytaleCommand()).execute(args1);
        assertEquals(0, exitCode);

        String[] args2 = {"rsa-naive", "encrypt", "-i", plainPath.toString(), 
            "-o", cipherPath.toString(), "-k", publicKeyPath.toString() };
        exitCode = new CommandLine(new ScytaleCommand()).execute(args2);
        assertEquals(0, exitCode);

        String[] args3 = {"rsa-naive", "decrypt", "-i", cipherPath.toString(),
            "-o", decryptedPath.toString(), "-k", privateKeyPath.toString()};
        exitCode = new CommandLine(new ScytaleCommand()).execute(args3);
        assertEquals(0, exitCode);
        
        byte[] original = Files.readAllBytes(plainPath);
        byte[] decrypted = Files.readAllBytes(decryptedPath);

        assertArrayEquals(original, decrypted);
    }
}
