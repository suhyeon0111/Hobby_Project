package com.hoppy.app.login.auth.filter;

import com.hoppy.app.login.auth.token.AuthToken;
import com.hoppy.app.login.auth.provider.AuthTokenProvider;
import com.hoppy.app.login.utils.HeaderUtil;
import com.hoppy.app.response.error.exception.ErrorCode;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final AuthTokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String tokenStr = HeaderUtil.getAccessToken(request);
            AuthToken token = tokenProvider.convertAuthToken(tokenStr);

            /**
             * 요청 헤더에 담긴 Jwt Token 유효성 검사
             */
/*            if(token.validate()) {
                Authentication authentication = tokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                System.out.println("유효하지 않은 토큰입니다.");
            }*/
            try {
                Authentication authentication = tokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (IllegalArgumentException e) {
                logger.error("적합하지 않은 토큰 형식입니다.");
                throw new JwtException(ErrorCode.IlLEGAL_JWT.getCode());
            } catch (ExpiredJwtException e) {
                logger.warn("토큰의 기한이 만료됐습니다.");
                throw new JwtException(ErrorCode.EXPIRED_ACCESS_TOKEN.getCode());
            } catch (SignatureException e) {
                logger.error("사용자 인증에 실패했습니다.");
                throw new JwtException(ErrorCode.SIGNATURE_INVALID_JWT.getCode());
            } catch (UnsupportedJwtException e) {
                logger.error("지원하지 않는 Token 입니다.");
                throw new JwtException(ErrorCode.UNSUPPORTED_JWT.getCode());
            }
        } else {
            logger.warn("couldn't find bearer string, will ignore the header");
        }
        filterChain.doFilter(request, response);
    }
}
