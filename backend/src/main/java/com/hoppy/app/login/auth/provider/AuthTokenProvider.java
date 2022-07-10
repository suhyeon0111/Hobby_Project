package com.hoppy.app.login.auth.provider;

import com.hoppy.app.login.auth.authentication.CustomUserDetails;
import com.hoppy.app.login.auth.exception.TokenValidFailedException;
import com.hoppy.app.login.auth.token.AuthToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuthTokenProvider {

    @Value("${app.auth.tokenExpiry}")
    private String expiry;
    private final Key key;

    private static final String AUTHORITIES_KEY = "ROLE_";

    public AuthTokenProvider(@Value("${app.auth.tokenSecret}") String secretKey) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public AuthToken createToken(String socialId, String expiry) {
        Date expiryDate = getExpiryDate(expiry);
        return new AuthToken(socialId, expiryDate, key);
    }

    public AuthToken createUserAuthToken(String socialId) {
        return createToken(socialId, expiry);
    }

    public AuthToken convertAuthToken(String token) {
        return new AuthToken(token, key);
    }

    public static Date getExpiryDate(String expiry) {
        return new Date(System.currentTimeMillis() + Long.parseLong(expiry));
    }

    /**
     * Jwt decode를 통해 사용자 고유식별 값인 socialId(pk)를 추출
     */
    public String getSocialId(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }

    public Authentication getAuthentication(AuthToken authToken) {

        if(authToken.validate()) {
            Claims claims = authToken.getTokenClaims();
            Collection<GrantedAuthority> authorities =
                    Arrays.stream(new String[] {claims.get(AUTHORITIES_KEY).toString()})
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());
            
            CustomUserDetails principal = CustomUserDetails.builder()
                    .id(Long.valueOf(claims.getSubject()))
                    .password("")
                    .authorities(authorities)
                    .build();

            return new UsernamePasswordAuthenticationToken(principal, authToken, authorities);
        } else {
            log.info("Token Valid Failed Exception.");
            throw new TokenValidFailedException();
        }
    }
}
