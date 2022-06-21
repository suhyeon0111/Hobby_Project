package com.hoppy.app.login.oauth.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import java.security.Key;
import java.util.Date;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class AuthToken {

    @Getter
    private final String token;
    private final Key key;

    private static final String AUTHORITIES_KEYS = "ROLE";

//    private static final String AUTHORITIES_KEY = "ROLE_USER";

    public AuthToken(String socialId, Date expiry, Key key) {
        this.key = key;
        this.token = createAuthToken(socialId, expiry);
    }

    private String createAuthToken(String socialId, Date expiry) {
        return Jwts.builder()
                .setSubject(socialId)
                .signWith(key, SignatureAlgorithm.HS256)
                .setExpiration(expiry)
                .compact();
    }

    public boolean validate() {
        return this.getTokenClaims() != null;
    }

    public Claims getTokenClaims() {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJwt(token).getBody();
        } catch (SecurityException e) {
            log.info("Invalid JWT signature.");
        } catch (MalformedJwtException e) {
            log.info("Invalid JWT token.");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported Jwt token");
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid");
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
