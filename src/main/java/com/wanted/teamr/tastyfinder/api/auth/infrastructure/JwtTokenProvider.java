package com.wanted.teamr.tastyfinder.api.auth.infrastructure;

import static com.wanted.teamr.tastyfinder.api.exception.domain.ErrorCode.*;
import static io.jsonwebtoken.SignatureAlgorithm.HS256;

import com.wanted.teamr.tastyfinder.api.auth.dto.TokenCreateResponse;
import com.wanted.teamr.tastyfinder.api.exception.domain.CustomException;
import com.wanted.teamr.tastyfinder.api.member.domain.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import java.security.Key;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtTokenProvider {

    public static final String CLAIMS_AUTH = "auth";
    public static final String GRANTTYPE_BEARER = "Bearer";

    private final long expirationTimeMillis;
    private final Key key;

    public JwtTokenProvider(@Value("${security.jwt.secret-key}") final String secretKey,
                            @Value("${security.jwt.expire-period}") final Long expirationTimeMillis) {
        byte[] secretByteKey = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(secretByteKey);
        this.expirationTimeMillis = expirationTimeMillis;
    }

    public TokenCreateResponse generateToken(final Member member) {
        final String accessToken = generateAccessToken(member);
        final String refreshToken = generateRefreshToken();
        return TokenCreateResponse.builder()
                .grantType(GRANTTYPE_BEARER)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public void validateToken(final String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token).getBody();
        } catch (SecurityException | MalformedJwtException e) {
            throw new CustomException(AUTH_JWT_INVALID, e);
        } catch (ExpiredJwtException e) {
            throw new CustomException(AUTH_JWT_EXPIRED, e);
        } catch (UnsupportedJwtException e) {
            throw new CustomException(AUTH_JWT_UNSUPPORTED, e);
        } catch (IllegalArgumentException e) {
            throw new CustomException(AUTH_JWT_CLAIMS_EMPTY, e);
        }
    }

    public Claims parseClaims(final String accessToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    private String generateAccessToken(final Member member) {
        final Date expiration = new Date(System.currentTimeMillis() + expirationTimeMillis);
        return Jwts.builder()
                .setSubject(member.getEmail())
                .claim(CLAIMS_AUTH, member.getRole())
                .setExpiration(expiration)
                .signWith(key, HS256)
                .compact();
    }

    private String generateRefreshToken() {
        final Date expiration = new Date(System.currentTimeMillis() + expirationTimeMillis * 24 * 7);
        return Jwts.builder()
                .setExpiration(expiration)
                .signWith(key, HS256)
                .compact();
    }
}
