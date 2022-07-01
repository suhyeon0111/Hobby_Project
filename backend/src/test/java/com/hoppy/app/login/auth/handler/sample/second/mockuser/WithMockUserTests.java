package com.hoppy.app.login.auth.handler.sample.second.mockuser;

import com.hoppy.app.login.auth.service.MessageService;
import javax.transaction.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@SpringBootTest
public class WithMockUserTests {

    MessageService messageService;

    /**
     * java.lang.Exception: 
     * Unexpected exception, expected<org.springframework.security.authentication.AuthenticationCredentialsNotFoundException> 
     * but was<java.lang.NullPointerException>
     */
    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void getMessageUnauthenticated() {
        messageService.getMessage();
    }

    @Test
    @WithMockUser
    public void getMessageWithMockUser() {
        String message = messageService.getMessage();
        System.out.println("message = " + message);
    }

    @Test
    @WithAnonymousUser
    public void anonymous() throws Exception {
        System.out.println("WithMockUserTests.anonymous");
        System.out.println("messageService.getMessage() = " + messageService.getMessage());
    }
    
    @Test
    @WithUserDetails
    public void getMessageWithUserDetails() {
        String message = messageService.getMessage();
        System.out.println("message = " + message);
    }
    
    @Test
    @WithMockCustomUser()
    @Transactional
    public void test() {
        System.out.println("messageService.getMessage() = " + messageService.getMessage());
    }
}
