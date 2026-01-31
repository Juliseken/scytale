package de.juliseken.scytale.text.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import de.juliseken.scytale.text.api.Text;

public class TextWriter {
    public void write(Text text, Path path) throws IOException {
        Files.writeString(path, text.toString());
    }
}
