package com.hoppy.app.community.service;

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

    public Reply findById(long replyId);
    public void likeReply(long memberId, long replyId);
}
