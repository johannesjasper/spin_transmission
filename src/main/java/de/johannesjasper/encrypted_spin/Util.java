package de.johannesjasper.encrypted_spin;

import lombok.SneakyThrows;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;

import java.io.StringReader;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class Util {

    public static RSAPublicKey readPublicKey(String publicPEM) throws Exception {
        KeyFactory factory = KeyFactory.getInstance("RSA");
        PemReader pemReader = new PemReader(new StringReader(publicPEM));
        PemObject pemObject = pemReader.readPemObject();
        byte[] content = pemObject.getContent();
        X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(content);
        return (RSAPublicKey) factory.generatePublic(pubKeySpec);
    }

    public static RSAPrivateKey readPrivateKey(String privatePEM) throws Exception {
        KeyFactory factory = KeyFactory.getInstance("RSA");

        PemReader pemReader = new PemReader(new StringReader(privatePEM));
        PemObject pemObject = pemReader.readPemObject();
        byte[] content = pemObject.getContent();
        PKCS8EncodedKeySpec privKeySpec = new PKCS8EncodedKeySpec(content);
        return (RSAPrivateKey) factory.generatePrivate(privKeySpec);
    }
}
