// Day 16: Generating and validating JWT tokens for stateless user authentication
// Topic: Spring Security & JWT Token Generation

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    // Generate a secure 256-bit secret key for HMAC-SHA
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final long jwtExpirationMs = 86400000; // 24 hours validity

    // Generate token for an authenticated user
    public String generateToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key)
                .compact();
    }

    // Validate token integrity and check expiration
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            System.err.println("Invalid or expired JWT token: " + e.getMessage());
            return false;
        }
    }
}

// Benefit: Replaces heavy database-backed sessions with stateless tokens. 
// The client holds this cryptographically signed string and passes it on every API call, 
// allowing the backend to verify the user identity instantly without checking the database.
