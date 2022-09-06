package com.hoppy.app.story.service;

import com.hoppy.app.story.dto.StoryReplyRequestDto;

// TODO: 스토리에 달린 댓글이 많을 경우, 무한 스크롤을 고려해야 함.
public interface StoryReplyService {

    public void uploadStoryReply(Long memberId, Long storyId, StoryReplyRequestDto dto);

    public void deleteStoryReply(Long memberId, Long replyId);
    public void likeStoryReply(Long memberId, Long replyId);
    public void dislikeStoryReply(Long memberId, Long replyId);
    public void likeOrDislikeStoryReply(Long memberId, Long replyId);

    // TODO: 대댓글 등록, 대댓글 삭제, 대댓글 좋아요 기능
}
