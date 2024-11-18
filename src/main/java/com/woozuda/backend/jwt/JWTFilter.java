package com.woozuda.backend.jwt;

import com.woozuda.backend.account.dto.CustomUserDetails;
import com.woozuda.backend.account.entity.UserEntity;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
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
        String authorization= request.getHeader("Authorization");

        //예외처리 ( Bearer 형태가 아닌 경우 )
        if (authorization == null || !authorization.startsWith("Bearer ")) {

            //System.out.println("token null");

            //다음 필터로 넘겨준다
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorization.split(" ")[1];

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

        //UserDetails에 회원 정보 객체 담기
        CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);

        //스프링 시큐리티 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        //세션에 사용자 등록 . stateless 로서 이번 1회만 적용 된다 .
        SecurityContextHolder.getContext().setAuthentication(authToken);

        //다음 필터를 이어간다
        filterChain.doFilter(request, response);
    }
}
