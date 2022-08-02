package com.hoppy.app.story.controller;

import com.hoppy.app.login.auth.authentication.CustomUserDetails;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.service.MemberService;
import com.hoppy.app.response.dto.ResponseDto;
import com.hoppy.app.response.service.ResponseService;
import com.hoppy.app.response.service.SuccessCode;
import com.hoppy.app.story.domain.story.Story;
import com.hoppy.app.story.dto.UploadStoryDto;
import com.hoppy.app.story.service.StoryManageService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/story")
public class StoryController {

    private final StoryManageService storyManageService;

    private final MemberService memberService;

    private final ResponseService responseService;

    @PostMapping
    public ResponseEntity<ResponseDto> uploadStory(@RequestBody @Valid UploadStoryDto dto, @AuthenticationPrincipal
            CustomUserDetails userDetails) {
        Member member = memberService.findMemberById(userDetails.getId());
        Story story = storyManageService.uploadStory(dto, member);

        storyManageService.saveStory(story);
        return responseService.successResult(SuccessCode.UPLOAD_STORY_SUCCESS);
    }
}
