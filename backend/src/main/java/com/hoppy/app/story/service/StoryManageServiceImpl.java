package com.hoppy.app.story.service;

import com.hoppy.app.member.domain.Member;
import com.hoppy.app.story.domain.story.Story;
import com.hoppy.app.story.dto.UploadStoryDto;
import com.hoppy.app.story.repository.StoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoryManageServiceImpl implements StoryManageService {

    private final StoryRepository storyRepository;

    @Override
    public void saveStory(Story story) {
        storyRepository.save(story);
    }

    @Override
    public Story uploadStory(UploadStoryDto dto, Member member) {
        return Story.of(dto, member);
    }
}
