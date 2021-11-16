package de.johannesjasper.encrypted_spin;

import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;
import java.util.List;


@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("spin")
@Data
public class SPINConfig {
    private String keystoreName;
    private String keystorePassword;
    private String keystoreAlias;

    private KeyStore keyStore;

    @PostConstruct
    @SneakyThrows
    private void loadKeyStore() {
        char[] pwdArray = keystorePassword.toCharArray();
        keyStore = KeyStore.getInstance("JKS");
        File file = ResourceUtils.getFile("classpath:"+keystoreName);
        keyStore.load(new FileInputStream(file), pwdArray);
    }

    @SneakyThrows
    public List<X509Certificate> getCertificateChain() {
        return Arrays.stream(keyStore.getCertificateChain(keystoreAlias)).map(cert -> (X509Certificate) cert).toList();
    }

    @SneakyThrows
    public RSAPublicKey getPublicKey() {
        return (RSAPublicKey) keyStore.getCertificate(keystoreAlias).getPublicKey();
    }

    @SneakyThrows
    public RSAPrivateKey getPrivateKey() {
        return (RSAPrivateKey) keyStore.getKey(keystoreAlias, keystorePassword.toCharArray());
    }
}
