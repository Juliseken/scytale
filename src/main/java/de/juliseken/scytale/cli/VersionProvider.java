package de.juliseken.scytale.cli;

import java.io.InputStream;
import java.util.Properties;

import picocli.CommandLine.IVersionProvider;

public class VersionProvider implements IVersionProvider {

    @Override
    public String[] getVersion() throws Exception {
        Properties props = new Properties();

        String resourcePath = "/META-INF/maven/de.juliseken.scytale/scytale/pom.properties";

        try (InputStream in = getClass().getResourceAsStream(resourcePath)) {
            if (in != null) {
                props.load(in);
                String version = props.getProperty("version", "unknown");
                return new String[]{ "Scytale " + version };
            } else {
                return new String[]{ "Scytale unknown version" };
            }
        }
    }
}
