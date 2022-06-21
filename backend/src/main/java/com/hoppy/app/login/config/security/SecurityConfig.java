package com.hoppy.app.login.config.security;

import com.hoppy.app.login.api.repository.user.UserRefreshTokenRepository;
import com.hoppy.app.login.config.properties.AppProperties;
import com.hoppy.app.login.config.properties.CorsProperties;
import com.hoppy.app.login.oauth.entity.RoleType;
import com.hoppy.app.login.oauth.exception.RestAuthenticationEntryPoint;
import com.hoppy.app.login.oauth.filter.TokenAuthenticationFilter;
import com.hoppy.app.login.oauth.handler.OAuth2AuthenticationFailureHandler;
import com.hoppy.app.login.oauth.handler.OAuth2AuthenticationSuccessHandler;
import com.hoppy.app.login.oauth.handler.TokenAccessDeniedHandler;
import com.hoppy.app.login.oauth.repository.OAuth2AuthorizationRequestBasedOnCookieRepository;
import com.hoppy.app.login.oauth.service.CustomOAuth2UserService;
import com.hoppy.app.login.oauth.service.CustomUserDetailService;
import com.hoppy.app.login.oauth.token.AuthTokenProvider;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    private final CorsProperties corsProperties;
    private final AppProperties appProperties;
    private final AuthTokenProvider authTokenProvider;
    private final CustomUserDetailService userDetailService;
    private final CustomOAuth2UserService oAuth2UserService;
    private final TokenAccessDeniedHandler tokenAccessDeniedHandler;
    private final UserRefreshTokenRepository userRefreshTokenRepository;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http
//                    .cors()
//                .and()
//                    .sessionManagement()
//                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                    .csrf().disable()
//                    .formLogin().disable()
//                    .httpBasic().disable()
//                    .exceptionHandling()
//                    .authenticationEntryPoint(new RestAuthenticationEntryPoint())
//                    .accessDeniedHandler(tokenAccessDeniedHandler)
//                .and()
//                    .authorizeRequests()
//                    .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
//                    .antMatchers("/api/**").hasAnyAuthority(RoleType.USER.getCode())
//                    .antMatchers("/api/**/admin/**").hasAnyAuthority(RoleType.ADMIN.getCode())
//                    .anyRequest().authenticated()
//                .and()
//                    .oauth2Login()
//                    .authorizationEndpoint()
//                    .baseUri("/oauth2/authorization")
//                    .authorizationRequestRepository(oAuth2AuthorizationRequestBasedOnCookieRepository())
//                .and()
//                    .redirectionEndpoint()
//                    .baseUri("/*/oauth2/code/*")
//                .and()
//                    .userInfoEndpoint()
//                    .userService(oAuth2UserService)
//                .and()
//                    .successHandler(oAuth2AuthenticationSuccessHandler())
//                    .failureHandler(oAuth2AuthenticationFailureHandler());
//        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        System.out.println("Security Config Running..");

        http.authorizeRequests()
                .anyRequest().permitAll()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    /*
    * auth 매니저 설정
    * */
    @Override
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    /*
    * security 설정 시, 사용할 인코더 설정
    * */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
    * 토큰 필터 설정
    * */
    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(authTokenProvider);
    }

    /*
    * 쿠키 기반 인가 Repository
    * 인가 응답을 연계하고 검증할 때 사용
    * */
    @Bean
    public OAuth2AuthorizationRequestBasedOnCookieRepository oAuth2AuthorizationRequestBasedOnCookieRepository() {
        return new OAuth2AuthorizationRequestBasedOnCookieRepository();
    }

    /*
    * Oauth 인증 성공 핸들러
    * */
    @Bean
    public OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler() {
        return new OAuth2AuthenticationSuccessHandler(
                authTokenProvider,
                appProperties,
                userRefreshTokenRepository,
                oAuth2AuthorizationRequestBasedOnCookieRepository()
        );
    }

    /*
    * Oauth 인증 실패 핸들러
    * */
    @Bean
    public OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler() {
        return new OAuth2AuthenticationFailureHandler(oAuth2AuthorizationRequestBasedOnCookieRepository());
    }

    /*
    * Cors settings
    * */
//    @Bean
//    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
//        UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
//
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        corsConfiguration.setAllowedHeaders(Arrays.asList(corsProperties.getAllowedHeaders().split(",")));
//        corsConfiguration.setAllowedMethods(Arrays.asList(corsProperties.getAllowedMethods().split(",")));
//        corsConfiguration.setAllowedOrigins(Arrays.asList(corsProperties.getAllowedOrigins().split(",")));
//        corsConfiguration.setAllowCredentials(true);
//        corsConfiguration.setMaxAge(corsConfiguration.getMaxAge());
//
//        corsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
//        return corsConfigurationSource;
//    }
}
