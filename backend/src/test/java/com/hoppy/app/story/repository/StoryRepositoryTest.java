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
import org.junit.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
        Member member = Member.builder()
                .username("최대한")
                .role(Role.USER)
                .id(8669L)
                .profileImageUrl("https://www.image.com/test")
                .socialType(SocialType.KAKAO)
                .email("test99@naver.com")
                .password("secret-key")
                .intro("잘부탁드립니다.")
                .build();

        memberRepository.save(member);

        for(int i = 1; i <= 5; i++) {
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
    @WithMockCustomUser(id = "8669")
    void sortingWithQueryMethod() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long memberId = Long.parseLong(authentication.getName());
        List<Story> stories = storyRepository.findByMemberIdOrderByIdDesc(memberId);
        stories.forEach(story -> {
            System.out.println("story = " + story);
        });
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
}