package de.juliseken.scytale.text.io;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class BlockInput {

    public List<byte[]> readBlocks(InputStream in, int blockSize, boolean withPadding) throws IOException {
        List<byte[]> blocks = new ArrayList<byte[]>();
        byte[] buffer = new byte[blockSize];
        boolean isPadded = false;

        int read;
        while ((read = in.read(buffer)) != -1) {
            if (read < blockSize) {
                byte paddingLen = (byte) (blockSize - read);
                byte[] padded = new byte[blockSize];
                System.arraycopy(buffer, 0, padded, 0, read);
                for (int i = read; i < blockSize; i++) {
                    padded[i] = paddingLen;
                }
                blocks.add(padded);
                isPadded = true;
            } else {
                blocks.add(buffer.clone());
            }
        }
        if (!isPadded && withPadding) {
            byte[] padding = new byte[blockSize];
            for (int i = 0; i < blockSize; i++) {
                padding[i] = (byte)blockSize;
            }
            blocks.add(padding);
        }
        return blocks;
    }

    public List<byte[]> readBlocks(Path path, int blockSize, boolean withPadding) throws IOException {
        try (InputStream in = Files.newInputStream(path)) {
            return readBlocks(in, blockSize, withPadding);
        }
    }
}
