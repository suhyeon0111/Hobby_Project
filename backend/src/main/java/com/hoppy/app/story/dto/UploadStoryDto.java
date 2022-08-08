package com.hoppy.app.story.dto;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UploadStoryDto {

    @NotBlank(message = "제목을 입력해주세요.")
    @Length(max = 20, message = "글자 제한을 초과하였습니다.")
    String title;

    @NotBlank(message = "내용을 입력해주세요.")
    @Length(max = 256, message = "글자 제한을 초과하였습니다.")
    String content;

    String filename;
}
