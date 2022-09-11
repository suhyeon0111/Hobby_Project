package com.hoppy.app.community.service;

import com.hoppy.app.community.domain.Post;
import com.hoppy.app.community.domain.ReReply;
import com.hoppy.app.community.domain.Reply;
import com.hoppy.app.community.dto.CreateReReplyDto;
import com.hoppy.app.community.dto.CreateReplyDto;
import com.hoppy.app.community.dto.UpdateReplyDto;
import com.hoppy.app.community.repository.ReReplyRepository;
import com.hoppy.app.community.repository.ReplyRepository;
import com.hoppy.app.like.domain.MemberReReplyLike;
import com.hoppy.app.like.domain.MemberReplyLike;
import com.hoppy.app.like.repository.MemberReReplyLikeRepository;
import com.hoppy.app.like.repository.MemberReplyLikeRepository;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.service.MemberService;
import com.hoppy.app.response.error.exception.BusinessException;
import com.hoppy.app.response.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * packageName    : com.hoppy.app.community.service
 * fileName       : ReplyServiceImpl
 * author         : Kim
 * date           : 2022-08-25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-08-25        Kim       최초 생성
 */
@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {

    private final MemberReplyLikeRepository memberReplyLikeRepository;
    private final MemberReReplyLikeRepository memberReReplyLikeRepository;
    private final PostService postService;
    private final MemberService memberService;
    private final ReplyRepository replyRepository;
    private final ReReplyRepository reReplyRepository;


    @Override
    @Transactional
    public Reply createReply(long memberId, CreateReplyDto createReplyDto) {
        Member member = memberService.findById(memberId);
        Post post = postService.findById(createReplyDto.getPostId());
        return replyRepository.save(createReplyDto.toReply(member, post));
    }

    @Override
    @Transactional
    public void deleteReply(long memberId, long replyId) {
        Reply reply = replyRepository.findByIdAndAuthorIdWithReReplies(replyId, memberId)
                 .orElseThrow(() -> new BusinessException(ErrorCode.REPLY_NOT_FOUND));

        if(!reply.getReReplies().isEmpty()) {
            reReplyRepository.deleteAllByList(reply.getReReplies().stream()
                    .map(R -> R.getId())
                    .collect(Collectors.toList())
            );
        }
        replyRepository.delete(reply);
    }

    @Override
    public Reply findReplyById(long replyId) {
        return replyRepository.findById(replyId)
                .orElseThrow(() -> new BusinessException(ErrorCode.REPLY_NOT_FOUND));
    }

    @Override
    @Transactional
    public void likeReply(long memberId, long replyId) {
        Optional<MemberReplyLike> opt = memberReplyLikeRepository.findByMemberIdAndReplyId(memberId, replyId);
        if(opt.isPresent()) return;

        Member member = memberService.findById(memberId);
        Reply reply = findReplyById(replyId);
        memberReplyLikeRepository.save(MemberReplyLike.of(member, reply));
    }

    @Override
    @Transactional
    public void updateReply(UpdateReplyDto updateReplyDto, long memberId, long replyId) {
        Reply reply = findReplyById(replyId);

        if(reply.getAuthor().getId() != memberId) return;

        if(updateReplyDto.getContent() != null && !updateReplyDto.getContent().isEmpty()) reply.setContent(updateReplyDto.getContent());
    }

    @Override
    @Transactional
    public void dislikeReply(long memberId, long replyId) {
        memberReplyLikeRepository.delete(
                memberReplyLikeRepository.findByMemberIdAndReplyId(memberId, replyId).orElseThrow()
        );
    }

    @Override
    @Transactional
    public ReReply createReReply(long memberId, CreateReReplyDto createReReplyDto) {
        Member member = memberService.findById(memberId);
        Reply reply = findReplyById(createReReplyDto.getReplyId());
        return reReplyRepository.save(createReReplyDto.toReReply(member, reply));
    }

    @Override
    @Transactional
    public void deleteReReply(long memberId, long reReplyId) {
        reReplyRepository.delete(
                reReplyRepository.findByIdAndAuthorId(reReplyId, memberId)
                        .orElseThrow(() -> new BusinessException(ErrorCode.REPLY_NOT_FOUND))
        );
    }

    @Override
    public ReReply findReReplyById(long reReplyId) {
        return reReplyRepository.findById(reReplyId)
                .orElseThrow(() -> new BusinessException(ErrorCode.REPLY_NOT_FOUND));
    }

    @Override
    @Transactional
    public void likeReReply(long memberId, long reReplyId) {
        Optional<MemberReReplyLike> opt = memberReReplyLikeRepository.findByMemberIdAndReplyId(memberId, reReplyId);
        if(opt.isPresent()) return;

        Member member = memberService.findById(memberId);
        ReReply reReply = findReReplyById(reReplyId);
        memberReReplyLikeRepository.save(MemberReReplyLike.of(member, reReply));
    }

    @Override
    @Transactional
    public void dislikeReReply(long memberId, long reReplyId) {
        memberReReplyLikeRepository.delete(
            memberReReplyLikeRepository.findByMemberIdAndReplyId(memberId, reReplyId).orElseThrow()
        );
    }

    @Override
    @Transactional
    public void updateReReply(UpdateReplyDto updateReplyDto, long memberId, long reReplyId) {
        ReReply reReply = findReReplyById(reReplyId);

        if(reReply.getAuthor().getId() != memberId) throw new BusinessException(ErrorCode.PERMISSION_ERROR);

        if(updateReplyDto.getContent() != null && !updateReplyDto.getContent().isEmpty()) reReply.setContent(updateReplyDto.getContent());
    }
}
