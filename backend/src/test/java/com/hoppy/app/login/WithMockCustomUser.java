package com.hoppy.app.login;

import com.hoppy.app.login.auth.SocialType;
import com.hoppy.app.member.Role;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.security.test.context.support.WithSecurityContext;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithMockCustomUser {

    String id() default "1234";
    String password() default "password";
    SocialType socialType() default SocialType.KAKAO;
    Role role() default Role.USER;
}