package de.johannesjasper.encrypted_spin;

import lombok.SneakyThrows;

import java.security.cert.X509Certificate;

public class Util {
    @SneakyThrows
    public static byte[] encodeCertificate(X509Certificate cert) {
        return cert.getEncoded();
    }
}
