package com.hoppy.app.story.service;

import com.hoppy.app.member.domain.Member;
import com.hoppy.app.story.domain.story.Story;
import com.hoppy.app.story.dto.UploadStoryDto;

public interface StoryManageService {

    public void saveStory(Story story);

    public Story uploadStory(UploadStoryDto dto, Member member);
}
