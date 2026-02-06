package de.juliseken.scytale.rsa.io.impl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

import de.juliseken.scytale.rsa.api.RSAPrivateKey;
import de.juliseken.scytale.rsa.api.RSAPublicKey;
import de.juliseken.scytale.rsa.io.api.RSAKeyWriter;

public class RSAPemKeyWriter implements RSAKeyWriter {

    @Override
    public void writePublic(RSAPublicKey publicKey, Path path) throws IOException {
        java.security.spec.RSAPublicKeySpec spec =
            new java.security.spec.RSAPublicKeySpec(publicKey.getModulus(), publicKey.getExponent());
        try {
            java.security.KeyFactory factory = java.security.KeyFactory.getInstance("RSA");
            java.security.PublicKey javaKey = factory.generatePublic(spec);
            byte[] der = javaKey.getEncoded();

            String base64 = Base64.getMimeEncoder(64, new byte[]{'\n'})
                                .encodeToString(der);

            String pem =
                "-----BEGIN PUBLIC KEY-----\n" +
                base64 + "\n" +
                "-----END PUBLIC KEY-----\n";
            Files.writeString(path, pem, StandardCharsets.US_ASCII);
        } catch (java.security.NoSuchAlgorithmException | java.security.spec.InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writePrivate(RSAPrivateKey privateKey, Path path) throws IOException {
        java.security.spec.RSAPrivateKeySpec spec =
            new java.security.spec.RSAPrivateKeySpec(privateKey.getModulus(), privateKey.getExponent());
        try {
            java.security.KeyFactory factory = java.security.KeyFactory.getInstance("RSA");
            java.security.PrivateKey javaKey = factory.generatePrivate(spec);
            byte[] der = javaKey.getEncoded();

            String base64 = Base64.getMimeEncoder(64, new byte[]{'\n'})
                                .encodeToString(der);

            String pem =
                "-----BEGIN PRIVATE KEY-----\n" +
                base64 + "\n" +
                "-----END PRIVATE KEY-----\n";
            Files.writeString(path, pem, StandardCharsets.US_ASCII);
        } catch (java.security.NoSuchAlgorithmException | java.security.spec.InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }
}
