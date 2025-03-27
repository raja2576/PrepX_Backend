package com.prepXBackend.config;

import java.security.Key;
import java.util.Date;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

    private static final String SECRET_KEY = "WW91clN1cGVyU2VjdXJlU2VjcmV0S2V5TXVzdEJlQXRMZWFzdDMyQnl0ZXM="; // ✅ Base64 encoded secret

    // ✅ Generate JWT Token with email claim
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)  // ✅ Store email as subject
                .claim("email", email) // ✅ Include email claim
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10-hour expiration
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // ✅ Correct signing key
                .compact();
    }

    // ✅ Extract Email (Username) from Token
    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject(); // ✅ Returns email stored in the token
    }

    // ✅ Validate JWT Token
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token);
            System.out.println("✅ Token is valid: " + token);
            return true;
        } catch (Exception e) {
            System.out.println("❌ Invalid token: " + e.getMessage());
            return false; // Token is invalid
        }
    }


    // ✅ Get Signing Key
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
