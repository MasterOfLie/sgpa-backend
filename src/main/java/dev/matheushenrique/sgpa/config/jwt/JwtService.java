package dev.matheushenrique.sgpa.config.jwt;

import dev.matheushenrique.sgpa.dto.AccessToken;
import dev.matheushenrique.sgpa.exception.InvalidTokenException;
import dev.matheushenrique.sgpa.models.Usuario;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtService {
    private final SecretKeyGenerator keyGenerator;

    public AccessToken getAccessToken(Usuario usuario) {
        var key = keyGenerator.getSecretKey();
        String token = Jwts.builder()
                .signWith(key)
                .subject(usuario.getEmail())
                .expiration(generateExpirationData())
                .claims(generateClaims(usuario))
                .compact();
        return new AccessToken(token);
    }

    public String getIdFromToken(String tokenJwt){
        try {
            return Jwts.parser().verifyWith(keyGenerator.getSecretKey()).build()
                    .parseSignedClaims(tokenJwt).getPayload().getSubject();
        }catch (JwtException e){
            throw new InvalidTokenException("Invalid token");
        }
    }

    private Date generateExpirationData(){
        var expirationMinutesToken = 60L;
        LocalDateTime now = LocalDateTime.now().plusMinutes(expirationMinutesToken);
        return Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
    }
    private Map<String, Object> generateClaims(Usuario usuario) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("name", usuario.getFirstName());
        return claims;
    }

}
