package com.hoppy.app.story.service;

import com.hoppy.app.member.domain.Member;
import com.hoppy.app.response.error.exception.BusinessException;
import com.hoppy.app.response.error.exception.ErrorCode;
import com.hoppy.app.story.domain.story.Story;
import com.hoppy.app.story.dto.UploadStoryDto;
import com.hoppy.app.story.repository.StoryRepository;
import java.util.Optional;
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

    @Override
    public Story updateStory(UploadStoryDto dto, Long storyId) {
        Optional<Story> optStory = storyRepository.findById(storyId);
        if(optStory.isEmpty() || optStory.get().isDeleted()) {
            throw new BusinessException(ErrorCode.STORY_NOT_FOUND);
        }
        optStory.ifPresent(selectStory -> {
            selectStory.setTitle(dto.getTitle());
            selectStory.setContent(dto.getContent());
            selectStory.setFilePath(dto.getFilename());
            storyRepository.save(selectStory);
        });
        return optStory.get();
    }

    @Override
    public void deleteStory(Long storyId) {
        Optional<Story> optStory = storyRepository.findById(storyId);
        if(optStory.isEmpty() || optStory.get().isDeleted()) {
            throw new BusinessException(ErrorCode.STORY_NOT_FOUND);
        }
        optStory.get().setDeleted(true);
        storyRepository.save(optStory.get());
    }
}
