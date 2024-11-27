package com.woozuda.backend.jwt;

import com.woozuda.backend.account.dto.CustomOAuth2User;
//import com.woozuda.backend.account.dto.CustomUserDetails;
import com.woozuda.backend.account.entity.UserEntity;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //Authorization 을 키 값으로 가지는 것을 찾음

        // 헤더 버전
        //String authorization= request.getHeader("Authorization");

        String authorization = null;

        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {

            System.out.println(cookie.getName());
            if (cookie.getName().equals("Authorization")) {

                authorization = cookie.getValue();
            }
        }

        //Authorization 헤더 검증
        if (authorization == null) {

            System.out.println("token null");
            filterChain.doFilter(request, response);

            //조건이 해당되면 메소드 종료 (필수)
            return;
        }

        /*
        //헤더 버전 예외처리 ( Bearer 형태가 아닌 경우 )
        if (authorization == null || !authorization.startsWith("Bearer ")) {

            //System.out.println("token null");

            //다음 필터로 넘겨준다
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorization.split(" ")[1];
        */

        String token = authorization;

        // 예외 처리 (토큰 소멸 여부)
        if (jwtUtil.isExpired(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        //토큰에서 username과 role 획득
        String username = jwtUtil.getUsername(token);
        String role = jwtUtil.getRole(token);

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setPassword("temp");
        userEntity.setRole(role);

        /*
        토큰 버전
        //UserDetails에 회원 정보 객체 담기
        CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);

        //스프링 시큐리티 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        */

        //세션에 사용자 등록 . stateless 로서 이번 1회만 적용 된다 .
        CustomOAuth2User customOAuth2User = new CustomOAuth2User(userEntity);
        Authentication authToken = new UsernamePasswordAuthenticationToken(customOAuth2User, null, customOAuth2User.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        //다음 필터를 이어간다
        filterChain.doFilter(request, response);
    }
}
