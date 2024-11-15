package com.woozuda.backend.config;

import com.woozuda.backend.jwt.JWTFilter;
import com.woozuda.backend.jwt.JWTUtil;
import com.woozuda.backend.jwt.LoginFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JWTUtil jwtUtil;

    // AbstractAuthenticationProcessingFilter 원본 코드에서
    // authenticationmanager를 수동 설정해야 한다고 되어 있음 (The filter requires that you set the <tt>authenticationManager</tt> property. )
    // AuthenticationManager가 인자로 받을 AuthenticationConfiguraion 객체 생성자 주입
    // https://stackoverflow.com/questions/51986766/spring-security-getauthenticationmanager-returns-null-within-custom-filter
    private final AuthenticationConfiguration authenticationConfiguration;

    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, JWTUtil jwtUtil) {
        this.authenticationConfiguration = authenticationConfiguration;
        this.jwtUtil = jwtUtil;
    }

    //AuthenticationManager Bean 등록
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


    //비밀번호 평문 -> 비문화
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        //csrf, formlogin, httpbasic 필터를 비활성화
        http
                .csrf((auth) -> auth.disable())
                .formLogin((auth) -> auth.disable())
                .httpBasic((auth) -> auth.disable());

        //경로별 인가 작업
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/login", "/", "/account/join", "/account/sample/alluser").permitAll()
                        .requestMatchers("/account/sample/admin").hasRole("ADMIN")
                        .anyRequest().authenticated());

        //UserNamePasswordAuthenticationFilter 자리에 커스텀 하게 만든 LoginFilter를 실행한다.
        //jwt 방식으로 구현하다 보니 , form login 을 비활성화했고, UserNamePasswordAuthenticationFilter 도 비활성화 되었음 (그래서 커스텀 구현이 필요)
        http
                .addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class)
                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil), UsernamePasswordAuthenticationFilter.class);


        //stateless 세션 설정
        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

}
