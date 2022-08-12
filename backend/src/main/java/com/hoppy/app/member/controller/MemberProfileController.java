package com.hoppy.app.member.controller;

import com.hoppy.app.login.auth.authentication.CustomUserDetails;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.dto.MyProfileDto;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberProfileController {

    private final MemberRepository memberRepository;
    private final ResponseService responseService;
    private final StoryRepository storyRepository;
    private final StoryManageService storyManageService;

    /**
     * 현재 로그인 한 사용자의 마이페이지 데이터를 반환
     */
    @GetMapping("/myprofile")
    public ResponseEntity<ResponseDto> showMyProfile(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Long memberId = userDetails.getId();
        Optional<Member> member = memberRepository.findById(memberId);

        if(member.isEmpty()) {
            throw new BusinessException(ErrorCode.MEMBER_NOT_FOUND);
        }
        List<StoryDetailDto> storyDetails = storyManageService.showStoriesInProfile(member.get());
        MyProfileDto myProfileDto = MyProfileDto.of(member.get(), storyDetails);
        return responseService.successResult(SuccessCode.SHOW_PROFILE_SUCCESS, myProfileDto);
    }

    @GetMapping("/userprofile")
    public ResponseEntity<ResponseDto> showUserProfile(@RequestParam("id") String id) {
        Long memberId = Long.parseLong(id);
        Optional<Member> member = memberRepository.findById(memberId);
        if(member.isEmpty() || member.get().isDeleted()) {
            throw new BusinessException(ErrorCode.DELETED_MEMBER);
        }
        List<StoryDetailDto> storyDetails = storyManageService.showStoriesInProfile(member.get());
        UserProfileDto userProfileDto = UserProfileDto.of(member.get(), storyDetails);
        return responseService.successResult(SuccessCode.SHOW_PROFILE_SUCCESS, userProfileDto);
    }
}
