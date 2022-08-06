package com.hoppy.app.community.service;

import static org.junit.jupiter.api.Assertions.*;

import com.hoppy.app.community.repository.PostRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/**
 * @author 태경 2022-08-06
 */
@DataJpaTest
@TestInstance(Lifecycle.PER_CLASS)
class PostServiceImplTest {

    // TODO: 2022.08.06. Post 서비스 테스트 코드 작성이 필요함

    @Autowired
    PostRepository postRepository;

    @BeforeAll
    void before() {

    }

    @Test
    void listPostByMeetingWithPaging() {

    }
}