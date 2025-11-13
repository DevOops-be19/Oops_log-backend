package com.devoops.oopslog.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 헤더에서 Authorization Token 추출
        String authorizationHeader = request.getHeader("Authorization");
        // 토큰 존재하는지 검사
        if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")){
//            log.info("토큰 인증 실패");
            filterChain.doFilter(request,response);
            return;
        }

        log.info("Authorization 헤더 : {}", authorizationHeader);
        String token = authorizationHeader.substring(7);
        log.info("토큰 : {}", token);

        // 토큰 유효성 검사
        if(jwtUtil.validateToken(token)){
            Authentication authentication = jwtUtil.getAuthentication(token);
            log.info("authentication 내용 : {}",authentication.toString());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request,response);
    }
}
