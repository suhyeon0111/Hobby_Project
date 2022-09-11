package com.hoppy.app.community.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

/**
 * packageName    : com.hoppy.app.community.dto
 * fileName       : UpdateReply
 * author         : Kim
 * date           : 2022-09-02
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-09-02        Kim       최초 생성
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateReplyDto {

    @Length(max = 256, message = "글자 제한을 초과하였습니다")
    String content;
}
