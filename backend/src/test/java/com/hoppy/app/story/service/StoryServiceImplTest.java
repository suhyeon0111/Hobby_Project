package com.hoppy.app.story.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoppy.app.like.domain.MemberStoryLike;
import com.hoppy.app.like.repository.MemberStoryLikeRepository;
import com.hoppy.app.login.WithMockCustomUser;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.repository.MemberRepository;
import com.hoppy.app.member.service.MemberService;
import com.hoppy.app.response.error.exception.BusinessException;
import com.hoppy.app.response.error.exception.ErrorCode;
import com.hoppy.app.story.domain.story.Story;
import com.hoppy.app.story.dto.PagingStoryDto;
import com.hoppy.app.story.repository.StoryRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
class StoryServiceImplTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberService memberService;
    
    @Autowired
    MemberStoryLikeRepository memberStoryLikeRepository;

    @Autowired
    StoryRepository storyRepository;

    @Autowired
    StoryService storyService;

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @AfterEach
    void clean() {
        memberStoryLikeRepository.deleteAll();
        storyRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @DisplayName("스토리 좋아요 기능 테스트")
    @Test
    @Transactional
    void storyLikeTest() {
        Member member = memberRepository.save(Member.builder()
                .id(8669L)
                .build()
        );
        Story story = storyRepository.save(Story.builder()
                .title("Story Like Test")
                .content("This is Test")
                .member(member)
                .build()
        );
        
        memberStoryLikeRepository.save(MemberStoryLike.of(member, story));
    }


    
/*  
    // 더 이상 조회할 스토리가 없을 시 예외 확인
    // Mock 관련 오류 발생하는 듯. 수정 필요
    @Test
    void no_more_story_exception() {
        final var lastId = 0L;
        Pageable pageable = PageRequest.of(0, 3);

        List<Story> storyList = storyRepository.findNextStoryOrderByIdDesc(Long.MAX_VALUE, pageable);
        assertThat(storyList.isEmpty()).isTrue();

        given(storyRepository.findNextStoryOrderByIdDesc(Long.MAX_VALUE, pageable)).willReturn(new ArrayList<>());
        BusinessException exception = org.junit.jupiter.api.Assertions.assertThrows(BusinessException.class,
                () -> storyService.pagingStory(lastId));
        org.junit.jupiter.api.Assertions.assertEquals(ErrorCode.NO_MORE_STORY, exception.getMessage());
    }*/
}