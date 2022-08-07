package com.hoppy.app.story.controller;

import com.hoppy.app.login.auth.authentication.CustomUserDetails;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.service.MemberService;
import com.hoppy.app.response.dto.ResponseDto;
import com.hoppy.app.response.service.ResponseService;
import com.hoppy.app.response.service.SuccessCode;
import com.hoppy.app.story.domain.story.Story;
import com.hoppy.app.story.dto.StoryDetailDto;
import com.hoppy.app.story.dto.UploadStoryDto;
import com.hoppy.app.story.service.StoryManageService;
import java.util.Optional;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/story")
public class StoryController {

    private final StoryManageService storyManageService;

    private final MemberService memberService;

    private final ResponseService responseService;

    @PostMapping("/upload")
    public ResponseEntity<ResponseDto> uploadStory(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody @Valid UploadStoryDto dto) {
        Member member = memberService.findMemberById(userDetails.getId());
        Story story = storyManageService.uploadStory(dto, member);
        storyManageService.saveStory(story, member);
        StoryDetailDto storyDetailDto = StoryDetailDto.of(story, member);
        return responseService.successResult(SuccessCode.UPLOAD_STORY_SUCCESS, storyDetailDto);
    }

    @PostMapping("/update")
    public ResponseEntity<ResponseDto> updateStory(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody @Valid UploadStoryDto dto, @RequestParam("id") String id) {
        Story story = storyManageService.updateStory(dto, Long.parseLong(id));
        Member member = memberService.findMemberById(userDetails.getId());
        StoryDetailDto storyDetailDto = StoryDetailDto.of(story, member);
        return responseService.successResult(SuccessCode.UPLOAD_STORY_SUCCESS, storyDetailDto);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteStory(@RequestParam("id") String id) {
        storyManageService.deleteStory(Long.parseLong(id));
        return responseService.successResult(SuccessCode.DELETE_STORY_SUCCESS);
    }
}
