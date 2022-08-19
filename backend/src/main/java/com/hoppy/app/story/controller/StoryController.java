package com.hoppy.app.story.controller;

import com.hoppy.app.login.auth.authentication.CustomUserDetails;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.service.MemberService;
import com.hoppy.app.response.dto.ResponseDto;
import com.hoppy.app.response.service.ResponseService;
import com.hoppy.app.response.service.SuccessCode;
import com.hoppy.app.story.domain.story.Story;
import com.hoppy.app.story.dto.PagingStoryDto;
import com.hoppy.app.story.dto.StoryDto;
import com.hoppy.app.story.dto.UploadStoryDto;
import com.hoppy.app.story.service.StoryService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/story")
public class StoryController {

    private final StoryService storyService;

    private final MemberService memberService;

    private final ResponseService responseService;

    @PostMapping
    public ResponseEntity<ResponseDto> uploadStory(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody @Valid UploadStoryDto dto) {
        Member member = memberService.findById(userDetails.getId());
        Story story = storyService.uploadStory(dto, member);
        storyService.saveStory(story, member);
        StoryDto storyDto = StoryDto.of(story, member);
        return responseService.successResult(SuccessCode.UPLOAD_STORY_SUCCESS, storyDto);
    }

    @PutMapping
    public ResponseEntity<ResponseDto> updateStory(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody @Valid UploadStoryDto dto, @RequestParam("id") String id) {
        Story story = storyService.updateStory(dto, Long.parseLong(id));
        Member member = memberService.findById(userDetails.getId());
        StoryDto storyDto = StoryDto.of(story, member);
        return responseService.successResult(SuccessCode.UPLOAD_STORY_SUCCESS, storyDto);
    }

    @DeleteMapping
    public ResponseEntity<ResponseDto> deleteStory(@RequestParam("id") String id) {
        storyService.deleteStory(Long.parseLong(id));
        return responseService.successResult(SuccessCode.DELETE_STORY_SUCCESS);
    }

    @GetMapping
    public ResponseEntity<ResponseDto> showStoryList(@AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam(value = "lastId", defaultValue = "0") long lastId) {
        PagingStoryDto pagingStory = storyService.pagingStory(lastId);
        return responseService.successResult(SuccessCode.INQUIRY_STORY_SUCCESS, pagingStory);
    }
}
