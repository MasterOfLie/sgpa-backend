package dev.matheushenrique.sgpa.config.jwt;

import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class SecretKeyGenerator {
    private SecretKey key;
    public SecretKey getSecretKey() {
        if (key == null) {
            key = Jwts.SIG.HS256.key().build();
        }
        return key;
    }
}
