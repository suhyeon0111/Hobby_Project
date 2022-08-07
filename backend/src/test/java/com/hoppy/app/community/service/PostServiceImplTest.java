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

/**
 * @author 태경 2022-08-07
 */
@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {

    @InjectMocks
    PostService postService;

    @Mock
    MemberService memberService;

    @Mock
    LikeManagerService likeManagerService;

    @Test
    void listToDtoList() {
        // given
        Member member
    }
}