package com.hoppy.app.story.service;

import com.hoppy.app.like.domain.MemberStoryReplyLike;
import com.hoppy.app.like.repository.MemberStoryLikeRepository;
import com.hoppy.app.like.repository.MemberStoryReplyLikeRepository;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.service.MemberService;
import com.hoppy.app.response.error.exception.BusinessException;
import com.hoppy.app.response.error.exception.ErrorCode;
import com.hoppy.app.story.domain.story.Story;
import com.hoppy.app.story.domain.story.StoryReply;
import com.hoppy.app.story.dto.StoryReplyRequestDto;
import com.hoppy.app.story.repository.StoryReReplyRepository;
import com.hoppy.app.story.repository.StoryReplyRepository;
import com.hoppy.app.story.repository.StoryRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.swing.text.html.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StoryReplyServiceImpl implements StoryReplyService {

    private final StoryRepository storyRepository;

    private final MemberStoryLikeRepository memberStoryLikeRepository;

    private final MemberStoryReplyLikeRepository memberStoryReplyLikeRepository;

    private final StoryService storyService;
    private final MemberService memberService;

    private final StoryReplyRepository storyReplyRepository;
    private final StoryReReplyRepository storyReReplyRepository;

    @Override
    public void uploadStoryReply(Long memberId, Long storyId, StoryReplyRequestDto dto) {
        Story story =  storyService.findByStoryId(storyId);
        Member member = memberService.findById(memberId);
        storyReplyRepository.save(dto.toEntity(member, story));
    }

    @Override
    @Transactional
    public void deleteStoryReply(Long memberId, Long replyId) {
        StoryReply reply = findReReplies(memberId, replyId);
        if(!reply.getReReplies().isEmpty()) {
            List<Long> idList = reply.getReReplies().stream().map(R -> R.getId()).collect(
                    Collectors.toList());
            storyReReplyRepository.deleteAllByList(idList);
        }
        storyReplyRepository.delete(reply);
    }

    public StoryReply findReReplies(Long memberId, Long replyId) {
        Optional<StoryReply> reply = storyReplyRepository.findByIdAndMemberIdWithReReplies(replyId, memberId);
        if(reply.isEmpty()) {
            throw new BusinessException(ErrorCode.REPLY_NOT_FOUND);
        }
        return reply.get();
    }

    public StoryReply findByReplyId(Long replyId) {
        Optional<StoryReply> reply = storyReplyRepository.findById(replyId);
        if(reply.isEmpty()) {
            throw new BusinessException(ErrorCode.REPLY_NOT_FOUND);
        }
        return reply.get();
    }

    @Override
    public void likeStoryReply(Long memberId, Long replyId) {
        Optional<MemberStoryReplyLike> optional =
                memberStoryReplyLikeRepository.findByMemberIdAndReplyId(memberId, replyId);
        if(optional.isPresent()) {
            return;
        }
        Member member = memberService.findById(memberId);
        StoryReply reply = findByReplyId(replyId);
        memberStoryReplyLikeRepository.save(MemberStoryReplyLike.of(member, reply));
    }
    @Override
    public void dislikeStoryReply(Long memberId, Long replyId) {
        Optional<MemberStoryReplyLike> optional =
                memberStoryReplyLikeRepository.findByMemberIdAndReplyId(memberId, replyId);
        if(optional.isPresent()) {
            memberStoryReplyLikeRepository.delete(optional.get());
        }
        else {
            return;
        }
    }
    @Override
    public void likeOrDislikeStoryReply(Long memberId, Long replyId) {
        Optional<MemberStoryReplyLike> optional =
                memberStoryReplyLikeRepository.findByMemberIdAndReplyId(memberId, replyId);
        if(optional.isPresent()) {
            memberStoryReplyLikeRepository.delete(optional.get());
        }
        else {
            Member member = memberService.findById(memberId);
            StoryReply reply = findByReplyId(replyId);
            memberStoryReplyLikeRepository.save(MemberStoryReplyLike.of(member, reply));
        }
    }
}