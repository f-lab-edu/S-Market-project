package com.flab.s_market.domains.security.service;

import com.flab.s_market.common.exception.CustomException;
import com.flab.s_market.common.exception.ErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {
    // JWT 서명을 위한 KEY 객체
    private final Key key;
    private static final String HEADER_ALGORITHM = "HS256";
    private static final String HEADER_TYPE = "JWT";
    private static final String AUTHORITIES_KEY = "auth";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 30*60*1000L; // 30분
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 7*24*60*60*1000L; // 7일
    private final Logger log = LoggerFactory.getLogger("JwtTokenProvider.class");

    // application.yml에서 secret 값 가져와서 key에 저장
    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
        // Base 64로 인코딩된 secret key 인코딩
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        // secret key를 이용해 key 객체 생성
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // Member 정보를 가지고 AccessToken, RefreshToken을 생성하는 메서드

    public String generateAccessToken(Authentication authentication){
        // 권한 가져오기
        String authorities = authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));

        // 토큰 발급 시간
        Date issueAt = new Date();

        // 현재 시간 가져오기
        long now = (new Date()).getTime();

        // access token 생성
        return Jwts.builder()
            .setHeader(createHeaders())
            .setSubject(authentication.getName()) // 토큰 주제 설정
            .claim(AUTHORITIES_KEY, authorities) // 사용자 권한 설정
            .setExpiration(new Date(now + ACCESS_TOKEN_EXPIRE_TIME)) // 토큰 만료시간 설정(30분)
            .setIssuedAt(issueAt)
            .signWith(key, SignatureAlgorithm.HS256) // 서명 알고리즘 생성
            .compact();
    }

    public String generateRefreshToken(){
        // 현재 시간 가져오기
        long now = (new Date()).getTime();

        return Jwts.builder()
            .setHeader(createHeaders())
            .setExpiration(new Date(now + REFRESH_TOKEN_EXPIRE_TIME)) // 토큰 만료시간 설정(일주일)
            .signWith(key, SignatureAlgorithm.HS256) // 서명 알고리즘 생성
            .compact();
    }

    private static Map<String, Object> createHeaders(){
        // header 부분 설정
        Map<String, Object> headers = new HashMap<>();
        // 알고리즘 정보
        headers.put("alg", HEADER_ALGORITHM);
        // 토큰 타입 정보
        headers.put("typ", HEADER_TYPE);
        return headers;
    }

    // Jwt 토큰을 복호화하여 토큰에 들어있는 정보를 꺼내, Authentication 객체를 생성하는 메서드
    public Authentication getAuthentication(String token) {
        // Jwt 토큰 복호화
        Claims claims = parseClaims(token);

        if (claims.get(AUTHORITIES_KEY) == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities =
            Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());

        // UserDetails 객체를 만들어서 Authentication return
        // UserDetails: interface, User: UserDetails를 구현한 class
        UserDetails principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    // jwt 토큰의 유효성을 검사하는 메서드
    public boolean validateToken(String token) {
        try {
            // 토큰을 파싱하여 유효성 검증
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
            // 유효한 토큰이라면 true 반환
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            // 토큰이 잘못된 경우 예외 처리
            throw new CustomException(ErrorCode.INVALID_TOKEN, Map.of("token", token), log::info, e);
        } catch (ExpiredJwtException e) {
            // 토큰이 만료된 경우 예외처리
            throw new CustomException(ErrorCode.EXPIRED_TOKEN, Map.of("token", token), log::info, e);
        } catch (UnsupportedJwtException | IllegalArgumentException e) {
            // 지원하지 않는 토큰이거나, 잘못된 형식의 토큰인 경우 예외처리
            throw new CustomException(ErrorCode.INVALID_TOKEN, Map.of("token", token), log::info, e);
        }
    }

    // jwt 토큰을 파싱해서 클레임 정보를 반환하는 메서드
    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(accessToken)
                .getBody();
        } catch (ExpiredJwtException e) {
            // 만료된 토큰이라면 클레임 정보를 반환
            return e.getClaims();
        }
    }
    public Long getExpiration(String accessToken) {
        // accessToken 남은 유효시간
        Date expiration = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody().getExpiration();
        // 현재 시간
        Long now = new Date().getTime();
        return (expiration.getTime() - now);
    }
}