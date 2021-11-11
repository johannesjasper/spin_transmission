package de.johannesjasper.encrypted_spin;

import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import static de.johannesjasper.encrypted_spin.Util.readCertificate;
import static de.johannesjasper.encrypted_spin.Util.readPrivateKey;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("spin")
@Data
public class SPINConfig {
    private String certificatePem;
    private String privatePem;

    @SneakyThrows
    public X509Certificate getCertificate() {
        return readCertificate(certificatePem);
    }

    @SneakyThrows
    public RSAPublicKey getPublicKey() {
        return (RSAPublicKey) getCertificate().getPublicKey();
    }

    @SneakyThrows
    public RSAPrivateKey getPrivateKey() {
        return readPrivateKey(privatePem);
    }
}
