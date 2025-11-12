package com.devoops.oopslog.security;

import com.devoops.oopslog.member.command.service.MemberCommandService;
import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collections;

@Configuration
public class WebSecurity {
    private Environment env;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final JwtUtil jwtUtil;
    private final MemberCommandService memberCommandService;

    @Autowired
    public WebSecurity(Environment env,
                       JwtAuthenticationProvider jwtAuthenticationProvider,
                       JwtUtil jwtUtil,
                       MemberCommandService memberCommandService) {
        this.env = env;     // JWT Token의 payload에 만료시간 만들다가 추가
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
        this.jwtUtil = jwtUtil;
        this.memberCommandService = memberCommandService;
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return new ProviderManager(Collections.singletonList(jwtAuthenticationProvider));
    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        /* 설명. Spring Security 모듈 추가 후 default 로그인 페이지 제거 및 인가 설정 */
        http.csrf(csrf -> csrf.disable());
        // oops 게시물, ooh 게시물, 공지사항, 로그인, 회원가입, 메인페이지
        http.authorizeHttpRequests(authz ->
                                   authz.requestMatchers("/**").permitAll()
                                        .requestMatchers(HttpMethod.GET, "/health").permitAll()
                                        .anyRequest()
                                        .authenticated()
                )
                /* 설명. Session 방식이 아닌 JWT Token 방식으로 인증된 회원(Authentication)을 Local Thread로 저장하겠다. */
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));




        // authenticationFilter를 추가하는 과정
        http.addFilter(new AuthenticationFilter(authenticationManager(),env,memberCommandService));

        // JwtFilter를 통한 토큰 검증 필터 추가
        http.addFilterBefore(new JwtFilter(jwtUtil),UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
