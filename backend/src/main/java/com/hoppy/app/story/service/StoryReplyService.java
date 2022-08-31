package com.hoppy.app.story.service;

import com.hoppy.app.story.dto.StoryReplyRequestDto;

public interface StoryReplyService {

    public void uploadStoryReply(Long memberId, Long storyId, StoryReplyRequestDto dto);

    public void deleteStoryReply(Long storyId, Long replyId);
    
}
