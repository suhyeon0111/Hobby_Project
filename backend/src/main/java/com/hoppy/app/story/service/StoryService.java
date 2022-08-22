package com.hoppy.app.story.service;

import com.hoppy.app.member.domain.Member;
import com.hoppy.app.story.domain.story.Story;
import com.hoppy.app.story.dto.PagingStoryDto;
import com.hoppy.app.story.dto.StoryDto;
import com.hoppy.app.story.dto.UploadStoryDto;
import java.util.List;

public interface StoryService {

    public void saveStory(Story story, Member member);

    public Story uploadStory(UploadStoryDto dto, Member member);

    public Story updateStory(UploadStoryDto dto, Long storyId);

    public void deleteStory(Long storyId);

    public List<StoryDto> showMyStoriesInProfile(Member member);

    public PagingStoryDto pagingStory(Long lastId);
}
