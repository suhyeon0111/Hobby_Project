package com.hoppy.app.member.controller;

import com.hoppy.app.login.auth.authentication.CustomUserDetails;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.dto.MyProfileDto;
import com.hoppy.app.member.dto.UpdateMemberDto;
import com.hoppy.app.member.dto.UserProfileDto;
import com.hoppy.app.member.repository.MemberRepository;
import com.hoppy.app.member.service.MemberServiceImpl;
import com.hoppy.app.response.dto.ResponseDto;
import com.hoppy.app.response.error.exception.BusinessException;
import com.hoppy.app.response.error.exception.ErrorCode;
import com.hoppy.app.response.service.ResponseService;
import com.hoppy.app.response.service.SuccessCode;
import com.hoppy.app.story.domain.story.Story;
import com.hoppy.app.story.dto.StoryDetailDto;
import com.hoppy.app.story.repository.StoryRepository;
import com.hoppy.app.story.service.StoryManageService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
public class MemberProfileController {

    private final MemberRepository memberRepository;
    private final ResponseService responseService;
    private final StoryManageService storyManageService;
    private final MemberServiceImpl memberService;

    @GetMapping
    public ResponseEntity<ResponseDto> showMyProfile(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Long memberId = userDetails.getId();
        Optional<Member> member = memberRepository.findById(memberId);
        if(member.isEmpty()) {
            throw new BusinessException(ErrorCode.MEMBER_NOT_FOUND);
        }

        MyProfileDto myProfileDto = MyProfileDto.of(member.get());
        return responseService.successResult(SuccessCode.SHOW_PROFILE_SUCCESS, myProfileDto);
    }

    @GetMapping("/story")
    private ResponseEntity<ResponseDto> showMyStories(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Long memberId = userDetails.getId();
        Optional<Member> member = memberRepository.findById(memberId);
        if(member.isEmpty()) {
            throw new BusinessException(ErrorCode.MEMBER_NOT_FOUND);
        }
        List<StoryDetailDto> storyDetails = storyManageService.showMyStoriesInProfile(member.get());
        return responseService.successResult(SuccessCode.SHOW_PROFILE_SUCCESS, storyDetails);
    }

    @GetMapping("/member")
    public ResponseEntity<ResponseDto> showUserProfile(@RequestParam("id") String id) {
        Long memberId = Long.parseLong(id);
        Optional<Member> member = memberRepository.findById(memberId);
        if(member.isEmpty() || member.get().isDeleted()) {
            throw new BusinessException(ErrorCode.DELETED_MEMBER);
        }
        UserProfileDto userProfileDto = UserProfileDto.of(member.get());
        return responseService.successResult(SuccessCode.SHOW_PROFILE_SUCCESS, userProfileDto);
    }

    @PutMapping
    public ResponseEntity<ResponseDto> updateUser(
            @AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody UpdateMemberDto memberDto) {
        Optional<Member> optMember = memberRepository.findById(userDetails.getId());
        if(optMember.isPresent()) {
            Member member = memberService.updateById(userDetails.getId(), memberDto);
            UpdateMemberDto updateMemberDto = UpdateMemberDto.of(member);
            return responseService.successResult(SuccessCode.UPDATE_SUCCESS, updateMemberDto);
        }
        throw new BusinessException(ErrorCode.MEMBER_NOT_FOUND);
    }
}
