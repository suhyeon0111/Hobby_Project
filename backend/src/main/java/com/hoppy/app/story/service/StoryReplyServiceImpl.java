package com.hoppy.app.story.service;

import com.hoppy.app.like.repository.MemberStoryLikeRepository;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.service.MemberService;
import com.hoppy.app.story.domain.story.Story;
import com.hoppy.app.story.dto.StoryReplyRequestDto;
import com.hoppy.app.story.repository.StoryReplyRepository;
import com.hoppy.app.story.repository.StoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StoryReplyServiceImpl implements StoryReplyService {

    private final StoryRepository storyRepository;

    private final MemberStoryLikeRepository memberStoryLikeRepository;

    private final StoryService storyService;
    private final MemberService memberService;

    private final StoryReplyRepository storyReplyRepository;

    @Override
    public void uploadStoryReply(Long memberId, Long storyId, StoryReplyRequestDto dto) {
        Story story =  storyService.findByStoryId(storyId);
        Member member = memberService.findById(memberId);
        storyReplyRepository.save(dto.toEntity(member, story));
    }

    @Override
    @Transactional
    public void deleteStoryReply(Long storyId, Long replyId) {
        // TODO: 댓글이 존재하지 않을 때 예외 처리
        // TODO: 작성자에 한하여 댓글 수정 및 삭제 권한 부여
        storyReplyRepository.deleteByStoryIdAndReplyId(storyId, replyId);
    }

    @Override
    public void likeStoryReply(Long memberId, Long replyId) {

    }
}
