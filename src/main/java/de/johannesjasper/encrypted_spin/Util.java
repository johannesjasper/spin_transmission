package de.johannesjasper.encrypted_spin;

import com.nimbusds.jose.util.X509CertUtils;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;

import java.io.StringReader;
import java.security.KeyFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

public class Util {

    public static X509Certificate readCertificate(String encodedCert) {
        return X509CertUtils.parse(encodedCert);
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
