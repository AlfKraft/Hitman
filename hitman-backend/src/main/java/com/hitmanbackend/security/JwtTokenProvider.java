package com.hitmanbackend.security;

import com.hitmanbackend.service.Account;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
    private static final byte[] keyBytes = Decoders.BASE64.decode("O2bPC+OsIvYc5fpjf7g2YZmh7Qjj1jd5Lxk/LZSfCCMtjnlu73f8r8FF7b47MqNPEq3ozPg9HdSN7qP3ymyZow==");
    public static final Key key = Keys.hmacShaKeyFor(keyBytes);

    public String generateJwt(String username, String authority) {
        Map<String, Object> claims = Map.of("role", authority);
        Key key = JwtTokenProvider.key;
        return Jwts.builder()
                .setIssuer("HITMAN")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 10))
                .setSubject(username)
                .addClaims(claims)
                .signWith(key)
                .compact();
    }

    private Boolean isTokenExpired(String token){
         Claims claims = parseToken(token);
         return claims.getExpiration().before(new Date());
    }

    public Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }


}
