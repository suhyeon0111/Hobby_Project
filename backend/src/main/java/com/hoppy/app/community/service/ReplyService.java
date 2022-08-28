package com.hoppy.app.community.service;

import com.hoppy.app.community.domain.ReReply;
import com.hoppy.app.community.domain.Reply;

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

    public Reply findReplyById(long replyId);
    public void likeReply(long memberId, long replyId);
    public void dislikeReply(long memberId, long replyId);
    public ReReply findReReplyById(long reReplyId);
    public void likeReReply(long memberId, long reReplyId);
    public void dislikeReReply(long memberId, long reReplyId);
}
