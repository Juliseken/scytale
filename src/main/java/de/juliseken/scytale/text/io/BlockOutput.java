package de.juliseken.scytale.text.io;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class BlockOutput {

    public void writeBlocks(List<byte[]> blocks, Path path, boolean withPadding) throws IOException {
        try (OutputStream out = Files.newOutputStream(path)) {
            for (int i = 0; i < blocks.size() - 1; i++) {
                out.write(blocks.get(i));
            }
            byte[] lastBlock = blocks.getLast();
            if (withPadding) {
                byte paddingLen = lastBlock[lastBlock.length - 1];
                for (int i = lastBlock.length - paddingLen; i < lastBlock.length; i++) {
                    if (lastBlock[i] != paddingLen) {
                        throw new RuntimeException("Invalid Padding");
                    }
                }
                if (paddingLen < lastBlock.length) {
                    byte[] lastBlockNew = new byte[lastBlock.length - paddingLen];
                    System.arraycopy(lastBlock, 0, lastBlockNew, 0, lastBlock.length - paddingLen);
                    out.write(lastBlockNew);
                }
            } else {
                out.write(lastBlock);
            }
        }
    }
}
