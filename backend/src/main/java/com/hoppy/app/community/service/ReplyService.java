package com.hoppy.app.community.service;

import com.hoppy.app.community.domain.ReReply;
import com.hoppy.app.community.domain.Reply;
import com.hoppy.app.community.dto.CreateReReplyDto;
import com.hoppy.app.community.dto.CreateReplyDto;

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

    public Reply createReply(long memberId, CreateReplyDto createReplyDto);
    public Reply findReplyById(long replyId);
    public void likeReply(long memberId, long replyId);
    public void dislikeReply(long memberId, long replyId);
    public ReReply createReReply(long memberId, CreateReReplyDto createReReplyDto);
    public ReReply findReReplyById(long reReplyId);
    public void likeReReply(long memberId, long reReplyId);
    public void dislikeReReply(long memberId, long reReplyId);
}
