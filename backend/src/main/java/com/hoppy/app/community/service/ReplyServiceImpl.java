package com.hoppy.app.community.service;

import com.hoppy.app.community.domain.Reply;
import com.hoppy.app.community.repository.ReplyRepository;
import com.hoppy.app.like.domain.MemberReplyLike;
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
    private final MemberService memberService;
    private final ReplyRepository replyRepository;


    @Override
    public Reply findById(long replyId) {
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
        Reply reply = findById(replyId);
        memberReplyLikeRepository.save(MemberReplyLike.of(member, reply));
    }
}
