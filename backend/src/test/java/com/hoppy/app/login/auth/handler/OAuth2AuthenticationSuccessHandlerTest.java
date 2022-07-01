package com.hoppy.app.login.auth.handler;

import static org.junit.jupiter.api.Assertions.*;

import com.hoppy.app.login.auth.service.MessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
class OAuth2AuthenticationSuccessHandlerTest {

    MessageService messageService;

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    void onAuthenticationSuccess() {
        messageService.getMessage();
    }
}