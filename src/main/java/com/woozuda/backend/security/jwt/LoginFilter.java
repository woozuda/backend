package com.woozuda.backend.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woozuda.backend.account.dto.CustomUser;
//import com.woozuda.backend.account.dto.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response){

        //String username = obtainUsername(request);
        //String password = obtainPassword(request);

        Map<String, String> jsonMap;

        ObjectMapper mapper = new ObjectMapper();
        try {
            jsonMap = mapper.readValue(request.getInputStream(), Map.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String username = jsonMap.get("username");
        String password = jsonMap.get("password");
        UsernamePasswordAuthenticationToken authRequest = UsernamePasswordAuthenticationToken.unauthenticated(username, password);

        setDetails(request, authRequest);

        //https://stackoverflow.com/questions/9787409/what-is-the-default-authenticationmanager-in-spring-security-how-does-it-authen
        // authenticate ->  AuthenticationManager -> userDetailsService 호출 순
        // 따라서 userDetailsService 를 구현 해야 합니다
        return authenticationManager.authenticate(authRequest);
    }

    // 로그인 성공 시 실행하는 메소드
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        CustomUser customUserDetails = (CustomUser) authentication.getPrincipal();

        String username = customUserDetails.getUsername();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        String token = jwtUtil.createJwt(username, role, 90*24*60*60*1000L);

        //쿠키로 수정 필요 ~~
        ResponseCookie responseCookie = createCookie("Authorization", token);
        response.addHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());

        response.sendRedirect("https://woozuda-test.vercel.app/home");
        //response.addCookie(createCookie("Authorization", token));

        //response.addHeader("Authorization", "Bearer " + token);

        //지워야 함 ㄴ
        //super.successfulAuthentication(request, response, chain, authentication);
    }

    // 로그인 실패 시 실행하는 메소드
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        response.setStatus(401);
    }

    private ResponseCookie createCookie(String key, String value) {

        /*
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(90*24*60*60);
        //cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        */
        ResponseCookie responseCookie = ResponseCookie.from(key, value)
                .httpOnly(false)
                .secure(true)
                .path("/")
                .maxAge(90 * 24 * 60 * 60)
                .sameSite("None")
                .build();


        return responseCookie;
    }
}