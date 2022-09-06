package com.hoppy.app.story.repository;

import static org.assertj.core.api.Assertions.*;

import com.hoppy.app.login.WithMockCustomUser;
import com.hoppy.app.login.auth.SocialType;
import com.hoppy.app.member.Role;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.repository.MemberRepository;
import com.hoppy.app.story.domain.story.Story;
import java.time.LocalDateTime;
import java.util.List;
import javax.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
class StoryRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    StoryRepository storyRepository;

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


    @AfterEach
    public void afterEach() {
        storyRepository.deleteAll();
        memberRepository.deleteAll();
    }


    @Test
    public void insertBaseTimeEntity() {
        LocalDateTime now = LocalDateTime.now();
        System.out.println("now = " + now);
        storyRepository.save(Story.builder()
                .title("title")
                .content("This is BaseTimeEntity Test")
                .build());
        List<Story> storyList = storyRepository.findAll();

        Story story = storyList.get(0);

        System.out.println(">>>>>>>> createDate=" + story.getCreatedDate() + ", modifiedDate=" + story.getModifiedDate());
    }

    @DisplayName("레포지토리 쿼리 정상 동작 테스트")
    @Test
    void repositoryPagingTest() throws Exception {
        List<Story> stories = storyRepository.findNextStoryOrderByIdDesc(Long.MAX_VALUE, PageRequest.of(0, 3));
        assertThat(stories.size()).isEqualTo(3);
    }
}