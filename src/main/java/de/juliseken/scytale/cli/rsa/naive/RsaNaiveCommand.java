package de.juliseken.scytale.cli.rsa.naive;

import picocli.CommandLine.Command;

@Command(
    name = "rsa-naive",
    description = "Naive RSA implementation for learning",
    subcommands = {
        RsaNaiveKeygenCommand.class,
        RsaNaiveEncryptCommand.class,
        RsaNaiveDecryptCommand.class
    }
)
public class RsaNaiveCommand {}
