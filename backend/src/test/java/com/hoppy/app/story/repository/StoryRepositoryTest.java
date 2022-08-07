package com.hoppy.app.story.repository;

import static org.assertj.core.api.Assertions.*;

import com.hoppy.app.login.auth.SocialType;
import com.hoppy.app.member.Role;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.repository.MemberRepository;
import com.hoppy.app.story.domain.story.Story;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StoryRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    StoryRepository storyRepository;

    @BeforeEach
    void setup() {
        memberRepository.save(
                Member.builder()
                        .username("최대한")
                        .role(Role.USER)
                        .id(8669L)
                        .profileImageUrl("https://www.image.com/test")
                        .socialType(SocialType.KAKAO)
                        .email("test99@naver.com")
                        .password("secret-key")
                        .intro("잘부탁드립니다.")
                        .build()
        );
    }

    @After
    public void cleanup() {
        storyRepository.deleteAll();
    }

    @Test
    public void insertBaseTimeEntity() {
        LocalDateTime now = LocalDateTime.now();
        storyRepository.save(Story.builder()
                .title("title")
                .content("This is BaseTimeEntity Test")
                .build());
        List<Story> storyList = storyRepository.findAll();

        Story story = storyList.get(0);

        System.out.println(">>>>>>>> createDate=" + story.getCreatedDate() + ", modifiedDate=" + story.getModifiedDate());
        assertThat(story.getCreatedDate()).isAfter(now);
        assertThat(story.getModifiedDate()).isAfter(now);
    }
}