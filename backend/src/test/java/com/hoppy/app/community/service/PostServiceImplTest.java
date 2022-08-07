package com.hoppy.app.community.service;

import static org.junit.jupiter.api.Assertions.*;

import com.hoppy.app.like.service.LikeManagerService;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.service.MemberService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author 태경 2022-08-07
 */
//@ExtendWith(MockitoExtension.class)
@SpringBootTest
class PostServiceImplTest {

//    @InjectMocks
    @Autowired
    PostService postService;

//    @Mock
    @Autowired
    MemberService memberService;

//    @Mock
    @Autowired
    LikeManagerService likeManagerService;

    @Test
    void listToDtoList() {
        // given
        Member member
    }
}