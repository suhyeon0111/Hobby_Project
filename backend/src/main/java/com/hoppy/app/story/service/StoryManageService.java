package com.hoppy.app.story.service;

import com.hoppy.app.member.domain.Member;
import com.hoppy.app.story.domain.story.Story;
import com.hoppy.app.story.dto.StoryDetailDto;
import com.hoppy.app.story.dto.UploadStoryDto;
import java.util.List;

public interface StoryManageService {

    public void saveStory(Story story, Member member);

    public Story uploadStory(UploadStoryDto dto, Member member);

    public Story updateStory(UploadStoryDto dto, Long storyId);

    public void deleteStory(Long storyId);

    public List<StoryDetailDto> showStoriesInProfile(Member member);
}