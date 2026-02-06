package de.juliseken.scytale.cli;

import de.juliseken.scytale.cli.rsa.naive.RsaNaiveCommand;
import picocli.CommandLine.Command;

@Command(
    name = "scytale",
    mixinStandardHelpOptions = true,
    versionProvider = VersionProvider.class,
    subcommands = {
        RsaNaiveCommand.class
    }
)
public class ScytaleCommand {}
