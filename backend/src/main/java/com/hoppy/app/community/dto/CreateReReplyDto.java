package com.hoppy.app.community.dto;

import com.hoppy.app.community.domain.ReReply;
import com.hoppy.app.community.domain.Reply;
import com.hoppy.app.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * packageName    : com.hoppy.app.community.dto
 * fileName       : CreateReplyDto
 * author         : Kim
 * date           : 2022-08-31
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-08-31        Kim       최초 생성
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateReReplyDto {

    @NotBlank(message = "댓글 ID 누락")
    Long replyId;

    @Length(max = 256, message = "글자 제한을 초과하였습니다")
    String content;

    public ReReply toReReply(Member author, Reply reply) {
        return ReReply.builder()
                .author(author)
                .reply(reply)
                .content(content)
                .build();
    }
}
