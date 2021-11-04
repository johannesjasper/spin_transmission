package de.johannesjasper.encrypted_spin;

import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.Cipher;
import java.security.Key;
import java.util.Base64;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class DecryptionController {

    public record DecryptionRequest(String ciphertext) {
    }

    private final SPINConfig config;

    @GetMapping("/jwk")
    public Map<String, Object> getJwk() {
        return new RSAKey.Builder(config.getPublicKey())
                .keyUse(KeyUse.ENCRYPTION)
                .algorithm(JWEAlgorithm.RSA_OAEP_512)
                .keyID("3fb99cbb-4773-4376-8d89-b216d83a6cab") // random
                .build()
                .toPublicJWK()
                .toJSONObject();
    }


    @PostMapping("/decrypt")
    public String decrypt(@RequestBody DecryptionRequest request) throws Exception {
        return decrypt(request.ciphertext, config.getPrivateKey());
    }

    private String decrypt(String cipherText, Key privKey) throws Exception {
        var cipher = Cipher.getInstance("RSA/None/OAEPWithSHA512AndMGF1Padding", "BC");
        cipher.init(Cipher.DECRYPT_MODE, privKey);
        return new String(cipher.doFinal(Base64.getDecoder().decode(cipherText)));
    }
}
