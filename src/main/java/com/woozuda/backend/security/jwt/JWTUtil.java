package com.woozuda.backend.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
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

    // jwt bearer 토큰으로 부터 username 을 추출
    public String getUsername(String jws) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(jws).getPayload().get("username", String.class);
    }

    // jwt bearer 토큰으로 부터 role 을 추출
    public String getRole(String jws) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(jws).getPayload().get("role", String.class);
    }

    // jwt bearer 토큰이 만료 되었는지 검사
    public Boolean isExpired(String jws) {
        //System.out.println(Jwts.parser().verifyWith(key).build().parseSignedClaims(jws).getPayload().getExpiration());
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(jws).getPayload().getExpiration().before(new Date());
    }

    // jwt 토큰 자체를 생성
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
