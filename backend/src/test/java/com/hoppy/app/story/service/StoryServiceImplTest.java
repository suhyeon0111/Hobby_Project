package com.hoppy.app.story.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.assertj.core.api.Assertions;
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
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class StoryServiceImplTest {

    @Autowired
    MemberRepository memberRepository;


    @Autowired
    StoryRepository storyRepository;

    @Autowired
    StoryService storyService;

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setup() {

        Member member1 = Member.builder().id(8669L).username("최대한").build();
        Member member2 = Member.builder().id(7601L).username("김태경").build();

        memberRepository.save(member1);
        memberRepository.save(member2);

        for(int i = 1; i <= 20; i++) {
            Member member = null;
            if(i % 2 == 0) {
                member = member1;
            } else if(i % 2 != 0) {
                member = member2;
            }
            storyRepository.save(
                    Story.builder()
                            .member(member)
                            .title(i+"th Story")
                            .content("This is " + i + "th Story")
                            .filePath(i+".jpg")
                            .member(member).build()
            );
        }
    }

    @DisplayName("스토리 서비스 페이징 정상 동작 테스트")
    @Test
    void pagingStory() {
        PagingStoryDto storyDetailDtoList = storyService.pagingStory(Long.MAX_VALUE);
        assertThat(storyDetailDtoList.getStoryList().size()).isEqualTo(3);
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