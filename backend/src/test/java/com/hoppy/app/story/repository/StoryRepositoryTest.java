package com.hoppy.app.story.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.hoppy.app.member.repository.MemberRepository;
import com.hoppy.app.member.repository.MemberStoryRepository;
import com.hoppy.app.story.domain.Story;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StoryRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    StoryRepository storyRepository;

    @Autowired
    MemberStoryRepository memberStoryRepository;

    @Test
    void setup() {
        Story story = Story.builder()
                .title("1st Story")
                .content("Hello, This is 1st Story")
                .username("ChoiDaehan")
                .filePath("xxx1.avi")
                .build();
        storyRepository.save(story);
    }
}