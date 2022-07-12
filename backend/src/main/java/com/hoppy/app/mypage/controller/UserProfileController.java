package com.hoppy.app.mypage.controller;

import com.hoppy.app.login.auth.authentication.CustomUserDetails;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.dto.MyProfileDto;
import com.hoppy.app.member.dto.UserProfileDto;
import com.hoppy.app.mypage.dto.MyPageMemberDto;
import com.hoppy.app.member.repository.MemberRepository;
import com.hoppy.app.mypage.service.UpdateMemberServiceImpl;
import com.hoppy.app.response.dto.ResponseDto;
import com.hoppy.app.response.error.exception.BusinessException;
import com.hoppy.app.response.error.exception.ErrorCode;
import com.hoppy.app.response.service.ResponseService;
import com.hoppy.app.response.service.SuccessCode;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserProfileController {

    private final MemberRepository memberRepository;
    private final UpdateMemberServiceImpl updateMemberServiceImpl;
    private final ResponseService responseService;

    /**
     * 현재 로그인 한 사용자 정보를 반환
     */

    /**
     * 실제 web 테스트 시엔 잘 동작하는데, Test (UserProfileControllerTest.java) 에서 Test에서 응답 Body가 비어있는 현상
     * MockHttpServletResponse:
     *            Status = 200
     *     Error message = null
     *           Headers = [Vary:"Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers", X-Content-Type-Options:"nosniff", X-XSS-Protection:"1; mode=block", Cache-Control:"no-cache, no-store, max-age=0, must-revalidate", Pragma:"no-cache", Expires:"0", X-Frame-Options:"DENY"]
     *      Content type = null
     *              Body =
     *     Forwarded URL = null
     *    Redirected URL = null
     *           Cookies = []
     */
/*    @GetMapping("/myProfile")
    public ResponseEntity<ResponseDto> showMyProfile(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Long memberId = userDetails.getId();
        Optional<Member> member = memberRepository.findById(memberId);
        if(member.isPresent()) {
            MyProfileDto myProfileDto = MyProfileDto.of(member.get());
            return responseService.successResult(SuccessCode.SHOW_PROFILE_SUCCESS, myProfileDto);
        } else {
            throw new BusinessException(ErrorCode.MEMBER_NOT_FOUND);
        }
    }*/

    @GetMapping("/myprofile")
    public ResponseEntity<?> showMyProfile(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Long memberId = userDetails.getId();
        Optional<Member> member = memberRepository.findById(memberId);
        if(member.isPresent()) {
            MyProfileDto myProfileDto = MyProfileDto.of(member.get());
            return ResponseEntity.ok().body(myProfileDto);
        } else {
            throw new BusinessException(ErrorCode.MEMBER_NOT_FOUND);
        }
    }
    /**
     * 탈퇴한 회원일 경우 예외 처리 추가
     * Member에 탈퇴 여부를 확인하는 필드 추가 필요
     */
    @GetMapping("/userProfile")
    public ResponseEntity<ResponseDto> showUserProfile(@RequestParam("id") String id) {
        Long memberId = Long.parseLong(id);
        Optional<Member> member = memberRepository.findById(memberId);
        /**
         * 탈퇴 여부 필드 추가시 member.isPresent()가 아닌,
         * if(member.isQuitMember())로 변경
         */
        if(member.isPresent()) {
            UserProfileDto userProfileDto = UserProfileDto.of(member.get());
            return responseService.successResult(SuccessCode.SHOW_PROFILE_SUCCESS, userProfileDto);
        } else {
            throw new BusinessException(ErrorCode.DELETED_MEMBER);
        }
    }
    /**
     * 'PUT' 요청으로 사용자 이름, 프로필 이미지, 소개글을 파라미터로 받아 멤버 정보 수정.
     * 수정된 내용을 응답으로 보냄. (return 타입 void로 설정해도 상관 없을 듯)
     */
    @PutMapping("/update")
    public MyPageMemberDto updateMember(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam("username") String username,
            @RequestParam("profileImageUrl") String profileImageUrl,
            @RequestParam("intro") String intro
    ) {
        Long memberId = userDetails.getId();
        Member member = updateMemberServiceImpl.updateMember(memberId, username, profileImageUrl, intro);
        return MyPageMemberDto.builder().username(member.getUsername()).profileUrl(member.getProfileImageUrl()).intro(member.getIntro()).build();
    }
}
