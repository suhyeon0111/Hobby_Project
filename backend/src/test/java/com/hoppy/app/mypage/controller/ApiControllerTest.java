package com.hoppy.app.mypage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

/**
 * @WebMvcTest
 * -JPA 기능이 동작하지 않는다. @Service, @Repository 등 사용 불가능.
 * -@Controller, @ControllerAdvice 사용 가능
 */
@WebMvcTest
public class ApiControllerTest {

    @Autowired
    MockMvc mvc;


}
