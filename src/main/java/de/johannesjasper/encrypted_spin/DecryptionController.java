package de.johannesjasper.encrypted_spin;

import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Map;
import java.util.Optional;

@RestController
@Slf4j
public class DecryptionController {

    public record DecryptionRequest(String ciphertext) {
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public class InvalidSpinException extends RuntimeException {
        public InvalidSpinException(String message) {
            super(message);
        }
    }

    private final SPINConfig config;
    private final Cipher cipher;
    private final RSAKey jwk;

    public DecryptionController(SPINConfig config) {
        this.config = config;
        this.cipher = initializeCiper();
        this.jwk = buildJWK();
    }

    @SneakyThrows
    private Cipher initializeCiper() {
        try {
            Cipher cipher = Cipher.getInstance("RSA/None/OAEPWithSHA512AndMGF1Padding", "BC");
            cipher.init(Cipher.DECRYPT_MODE, config.getPrivateKey());
            return cipher;
        } catch (NoSuchAlgorithmException | NoSuchProviderException | NoSuchPaddingException | InvalidKeyException e) {
            log.error("Unable to initialize Cipher.", e);
            throw e;
        }
    }

    @SneakyThrows
    private RSAKey buildJWK() {
        var x5c = config.getCertificateChain().stream()
                .map(Util::encodeCertificate)
                .map(com.nimbusds.jose.util.Base64::encode)
                .toList();
        return new RSAKey.Builder(config.getPublicKey())
                .keyUse(KeyUse.ENCRYPTION)
                .algorithm(JWEAlgorithm.RSA_OAEP_512)
                .keyID("3fb99cbb-4773-4376-8d89-b216d83a6cab")
                .x509CertChain(x5c)
                .build()
                .toPublicJWK();
    }


    @GetMapping(value = "/api/publickey/spin", produces = "application/jwk+json")
    public Map<String, Object> getJwk() {
        return jwk.toJSONObject();
    }


    @PostMapping("/decrypt")
    public String decrypt(@RequestBody DecryptionRequest request) {
        return decrypt(request.ciphertext).orElseThrow(() -> new InvalidSpinException("Could not decrypt ciphertext"));
    }

    private Optional<String> decrypt(String cipherText) {
        try {
            return Optional.of(new String(cipher.doFinal(Base64.decode(cipherText))));
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            log.error("Could not decrypt ciphertext", e);
            return Optional.empty();
        }
    }
}
