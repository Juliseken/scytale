package de.juliseken.scytale.text.io;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class BlockIOTest {

    @Test
    public void testBlockIO(@TempDir Path tempDir) throws IOException {
        Path messagePath = tempDir.resolve("message.txt");
        Path readMessagePath = tempDir.resolve("readMessage.txt");
        String plaintext = "Hello RSA roundtrip!";
        BlockInput blockInput = new BlockInput();
        BlockOutput blockOutput = new BlockOutput();

        Files.writeString(messagePath, plaintext, StandardCharsets.UTF_8);
        List<byte[]> blocks = blockInput.readBlocks(messagePath, 8, true);

        blockOutput.writeBlocks(blocks, readMessagePath, true);
        
        byte[] original = Files.readAllBytes(messagePath);
        byte[] decrypted = Files.readAllBytes(readMessagePath);

        // Assert
        assertArrayEquals(original, decrypted);
    }
}
