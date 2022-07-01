package com.hoppy.app.login.auth.handler.sample.second.mockuser;

import com.hoppy.app.login.auth.handler.sample.second.mockuser.WithMockCustomUserSecurityContextFactory;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithSecurityContext;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
//@WithSecurityContext(factory = WithUserDetailsSecurityContextFactory.class, setupBefore = TestExecutionEvent.TEST_EXECUTION)
public @interface WithMockCustomUser {

    String username() default "rob";

    String name() default "Rob Winch";
}
