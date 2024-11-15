package com.woozuda.backend.jwt;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTUtil {

    private SecretKey key;

    public JWTUtil(@Value("${spring.jwt.secret}") String secret) {
        key = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    // 참조할만한 공식문서 구문
    // https://github.com/jwtk/jjwt?tab=readme-ov-file#quickstart
    // https://github.com/jwtk/jjwt?tab=readme-ov-file#parsing-of-custom-claim-types
    public String getUsername(String jws) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(jws).getPayload().get("username", String.class);
    }

    public String getRole(String jws) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(jws).getPayload().get("role", String.class);
    }

    public Boolean isExpired(String jws) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(jws).getPayload().getExpiration().before(new Date());
    }

    public String createJwt(String username, String role, Long expiredMs) {

        return Jwts.builder()
                .claim("username", username)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(key)
                .compact();
    }
}
