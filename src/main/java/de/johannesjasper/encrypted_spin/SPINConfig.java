package de.johannesjasper.encrypted_spin;

import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import static de.johannesjasper.encrypted_spin.Util.readPrivateKey;
import static de.johannesjasper.encrypted_spin.Util.readPublicKey;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("spin")
@Data
public class SPINConfig {
    private String publicPem;
    private String privatePem;

    @SneakyThrows
    public RSAPublicKey getPublicKey() {
        return readPublicKey(publicPem);
    }

    @SneakyThrows
    public RSAPrivateKey getPrivateKey() {
        return readPrivateKey(privatePem);
    }
}
