package com.hoppy.app.login.auth.token;

import com.hoppy.app.member.Role;
import com.hoppy.app.response.error.exception.ErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import java.security.Key;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
@RequiredArgsConstructor
public class AuthToken {
    private final String token;
    private final Key key;

    private static final String AUTHORITIES_KEYS = "ROLE_";

    public AuthToken(String socialId, Date expiry, Key key) {
        this.key = key;
        this.token = createAuthToken(socialId, expiry);
    }

    private String createAuthToken(String socialId, Date expiry) {
        return Jwts.builder()
                .setSubject(socialId)
                .claim(AUTHORITIES_KEYS, Role.USER)
                .signWith(key, SignatureAlgorithm.HS256)
                .setExpiration(expiry)
                .compact();
    }

    public boolean validate() {
        return this.getTokenClaims() != null;
    }

    public Claims getTokenClaims() {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signature.");
            throw new JwtException(ErrorCode.WRONG_TYPE_TOKEN.getCode());
        } catch (UnsupportedJwtException e) {
            log.error("지원하지 않는 Token 입니다.");
            throw new JwtException(ErrorCode.UNSUPPORTED_JWT.getCode());
        } catch (IllegalArgumentException e) {
            log.error("적합하지 않은 토큰 형식입니다.");
            throw new JwtException(ErrorCode.IlLEGAL_JWT.getCode());
        } catch (ExpiredJwtException e) {
            log.warn("토큰의 기한이 만료됐습니다.");
            throw new JwtException(ErrorCode.EXPIRED_ACCESS_TOKEN.getCode());
        } catch (SignatureException e) {
            log.error("사용자 인증에 실패했습니다.");
            throw new JwtException(ErrorCode.SIGNATURE_INVALID_JWT.getCode());
        } catch (Exception e) {
            log.error("Exception Message: {}", e.getMessage());
            throw new JwtException(ErrorCode.UNKNOWN_JWT_ERROR.getCode());
        }
    }

    public Claims getExpiredTokenClaims() {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token.");
            return e.getClaims();
        }
        return null;
    }

    public String getToken() {
        return this.token;
    }

    public Key getKey() {
        return this.key;
    }
}
