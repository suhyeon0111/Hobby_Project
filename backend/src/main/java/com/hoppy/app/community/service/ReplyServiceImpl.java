package com.hoppy.app.community.service;

import com.hoppy.app.community.domain.ReReply;
import com.hoppy.app.community.domain.Reply;
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

import java.util.Optional;

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
    private final MemberService memberService;
    private final ReplyRepository replyRepository;
    private final ReReplyRepository reReplyRepository;


    @Override
    public Reply findReplyById(long replyId) {
        Optional<Reply> opt = replyRepository.findById(replyId);
        if(opt.isEmpty()) {
            throw new BusinessException(ErrorCode.REPLY_NOT_FOUND);
        }
        return opt.get();
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
    public void dislikeReply(long memberId, long replyId) {
        memberReplyLikeRepository.deleteByMemberIdAndReplyId(memberId, replyId);
    }

    @Override
    public ReReply findReReplyById(long reReplyId) {
        Optional<ReReply> opt = reReplyRepository.findById(reReplyId);
        if(opt.isEmpty()) {
            throw new BusinessException(ErrorCode.REPLY_NOT_FOUND);
        }
        return opt.get();
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
        memberReReplyLikeRepository.deleteByMemberIdAndReplyId(memberId, reReplyId);
    }
}
