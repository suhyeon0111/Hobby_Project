package com.hoppy.app.community.service;

import com.hoppy.app.community.domain.ReReply;
import com.hoppy.app.community.domain.Reply;
import com.hoppy.app.community.dto.CreateReReplyDto;
import com.hoppy.app.community.dto.CreateReplyDto;

import java.util.List;

/**
 * packageName    : com.hoppy.app.community.service
 * fileName       : ReplyService
 * author         : Kim
 * date           : 2022-08-25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-08-25        Kim       최초 생성
 */
public interface ReplyService {

    Reply createReply(long memberId, CreateReplyDto createReplyDto);
    void deleteReply(long memberId, long replyId);
    Reply findReplyById(long replyId);
    void likeReply(long memberId, long replyId);
    void dislikeReply(long memberId, long replyId);
    ReReply createReReply(long memberId, CreateReReplyDto createReReplyDto);
    void deleteReReply(long memberId, long reReplyId);
    ReReply findReReplyById(long reReplyId);
    void likeReReply(long memberId, long reReplyId);
    void dislikeReReply(long memberId, long reReplyId);
}
