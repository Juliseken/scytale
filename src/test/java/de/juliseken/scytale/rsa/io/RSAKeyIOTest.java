package de.juliseken.scytale.rsa.io;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.juliseken.scytale.rsa.api.RSAPrivateKey;
import de.juliseken.scytale.rsa.api.RSAPublicKey;
import de.juliseken.scytale.rsa.impl.RSAPrivateKeyImpl;
import de.juliseken.scytale.rsa.impl.RSAPublicKeyImpl;

public class RSAKeyIOTest {

    @Test
    public void testPublicKeyIO(@TempDir Path tempDir) throws IOException {
        Path pubPath = tempDir.resolve("rsa-naive.pub");
        RSAPublicKey pub = new RSAPublicKeyImpl(BigInteger.valueOf(19), BigInteger.valueOf(60));
        RSAKeyWriter w = new RSAKeyWriter();
        RSAKeyReader r = new RSAKeyReader();

        w.write(pub, pubPath.toString());
        RSAPublicKey pubFromFile = r.readPublic(pubPath.toString());

        assertEquals(pub, pubFromFile);
    }

    @Test
    public void testPrivateKeyIO(@TempDir Path tempDir) throws IOException {
        Path privPath = tempDir.resolve("rsa-naive");
        RSAPrivateKey priv = new RSAPrivateKeyImpl(BigInteger.valueOf(19), BigInteger.valueOf(60));
        RSAKeyWriter w = new RSAKeyWriter();
        RSAKeyReader r = new RSAKeyReader();

        w.write(priv, privPath.toString());
        RSAPrivateKey privFromFile = r.readPrivate(privPath.toString());

        assertEquals(priv, privFromFile);
    }
}
