package com.hoppy.app.login.auth.filter;

import com.hoppy.app.response.error.ErrorResponse;
import com.hoppy.app.response.error.exception.ErrorCode;
import io.jsonwebtoken.JwtException;
import java.io.IOException;
import java.util.Arrays;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class TokenExceptionFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);  // go to 'TokenAuthenticationFilter'
        } catch (JwtException ex) {
            setErrorResponse(response, ex);
        }
    }

    public void setErrorResponse(HttpServletResponse response, Throwable ex)
            throws IOException {

        ErrorCode errorCode = ErrorCode.of(ex.getMessage());
        final ErrorResponse errorResponse = ErrorResponse.of(errorCode);

        response.setContentType("application/json; charset=UTF-8");
        response.setStatus(errorResponse.getStatus());
        response.getWriter().write(errorResponse.getMessage());
    }
}
