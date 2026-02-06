package de.juliseken.scytale.cli.rsa.naive;

import de.juliseken.scytale.cli.VersionProvider;
import picocli.CommandLine.Command;

@Command(
    name = "rsa-naive",
    description = "Naive RSA implementation for learning",
    mixinStandardHelpOptions = true,
    versionProvider = VersionProvider.class,
    subcommands = {
        RsaNaiveKeygenCommand.class,
        RsaNaiveEncryptCommand.class,
        RsaNaiveDecryptCommand.class
    }
)
public class RsaNaiveCommand {}
