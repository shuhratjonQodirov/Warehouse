package uz.qodirov.warehouse.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import uz.qodirov.warehouse.config.UserPrincipal;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtProvider {

    private static final long EXPIRE_TIME = 86400 * 1000L; // 1 kun (24 hour)

    private final String secretString = "qodirov_k.Uz_qanchalik_uzun_bolsa_shuncha_xavfsiz_qodirov_extra_secure_2026";

    private SecretKey key;

    @PostConstruct
    public void init() {
        String encodedSecret = Base64.getEncoder().encodeToString(secretString.getBytes(StandardCharsets.UTF_8));
        this.key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(encodedSecret));
    }

    public String generateToken(Authentication authentication) {
        UserPrincipal user = (UserPrincipal) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRE_TIME);

        return Jwts.builder()
                .subject(user.getUsername())
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(key)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            validateTokenAndGetClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        return validateTokenAndGetClaims(token).getSubject();
    }

    public Claims validateTokenAndGetClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}

