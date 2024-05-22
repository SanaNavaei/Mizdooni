package mizdooni.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import mizdooni.model.user.User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtService {
    private static final String ISSUER = "Mizdooni";
    private static final String SECRET_KEY = "mizdooni2024mizdooni2024mizdooni2024";
    private static final Duration EXPIRATION_DURATION = Duration.ofDays(1);

    public String createToken(User user) {
        return Jwts.builder().header().type("JWT").and()
                .issuer(ISSUER)
                .subject(String.valueOf(user.getId()))
                .claim("name", user.getUsername())
                .claim("email", user.getEmail())
                .issuedAt(new Date())
                .expiration(getExpirationTime())
                .signWith(getSignKey())
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> jwt = Jwts.parser().verifyWith(getSignKey()).build().parseSignedClaims(token);
            Claims payload = jwt.getPayload();
            return payload.getIssuer().equals(ISSUER) &&
                    payload.getSubject() != null &&
                    payload.get("name", String.class) != null &&
                    payload.get("email", String.class) != null &&
                    payload.getIssuedAt().before(new Date()) &&
                    payload.getExpiration().after(new Date());
        } catch (Exception ex) {
            return false;
        }
    }

    public int getUserId(String token) {
        String subject = Jwts.parser().verifyWith(getSignKey()).build().parseSignedClaims(token).getPayload().getSubject();
        return Integer.parseInt(subject);
    }

    private SecretKey getSignKey() {
        return new SecretKeySpec(SECRET_KEY.getBytes(), SignatureAlgorithm.HS256.getJcaName());
    }

    private Date getExpirationTime() {
        LocalDateTime exp = LocalDateTime.now().plus(EXPIRATION_DURATION);
        return Date.from(exp.atZone(ZoneId.systemDefault()).toInstant());
    }
}
