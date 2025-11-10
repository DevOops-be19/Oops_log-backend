package com.devoops.oopslog.security;

import com.devoops.oopslog.member.command.service.MemberCommandService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;

@Slf4j
@Component
public class JwtUtil {
    private final Key key;
    private final MemberCommandService memberCommandService;

    public JwtUtil(@Value("${token.secret}")String key,
                   MemberCommandService memberCommandService){
        byte[] keyBytes = Decoders.BASE64.decode(key);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.memberCommandService = memberCommandService;
    }

    public boolean validateToken(String token) {
        try{
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e){
            log.info("유효하지 않은 JWT Token(값이 없음)");
        } catch(ExpiredJwtException e){
            log.info("세션 만료");
        } catch(UnsupportedJwtException e){
            log.info("지원하지 않는 JWT Token");
        } catch(IllegalArgumentException e){
            log.info("토큰의 클레임이 없음");
        }
        return false;
    }

    public Authentication getAuthentication(String token) {
        // 토큰에 있는 claims 추출
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();

        // 토큰에 들어있던 member_id로 유효성 검증
        UserDetails userDetails = memberCommandService.loadUserByUsername(claims.getSubject());
//        log.info("userDetails: {}",userDetails.getUsername());
//        log.info("userDetails: {}",userDetails.getAuthorities());

        // 토큰에 있는 권한 추출



        //

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}
