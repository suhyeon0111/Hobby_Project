package com.hoppy.app.login.auth.handler.sample.first;

import com.hoppy.app.member.Role;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.security.test.context.support.WithSecurityContext;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithMockCustomUser {

    String username() default "max";

    Role role() default Role.USER;

//    String name() default "Choi max";
}
