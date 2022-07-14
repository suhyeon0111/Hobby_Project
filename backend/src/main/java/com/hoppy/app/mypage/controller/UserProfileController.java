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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
     * 현재 로그인 한 사용자의 마이페이지 데이터를 반환
     */
    @GetMapping("/myprofile")
    public ResponseEntity<ResponseDto> showMyProfile(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Long memberId = userDetails.getId();
        Optional<Member> member = memberRepository.findById(memberId);

        if(member.isEmpty()) {
            throw new BusinessException(ErrorCode.MEMBER_NOT_FOUND);
        }
        MyProfileDto myProfileDto = MyProfileDto.of(member.get());
        return responseService.successResult(SuccessCode.SHOW_PROFILE_SUCCESS, myProfileDto);
    }

    /**
     * 탈퇴한 회원일 경우 예외 처리 추가
     * Member에 탈퇴 여부를 확인하는 필드 추가 필요
     */
    @GetMapping("/userprofile")
    public ResponseEntity<ResponseDto> showUserProfile(@RequestParam("id") String id) {
        Long memberId = Long.parseLong(id);
        Optional<Member> member = memberRepository.findById(memberId);
        /**
         * 탈퇴 여부 필드 추가시 member.isPresent()가 아닌,
         * if(member.isQuitMember())로 변경
         */
        if(member.isEmpty()) {
            throw new BusinessException(ErrorCode.DELETED_MEMBER);
        }
        UserProfileDto userProfileDto = UserProfileDto.of(member.get());
        return responseService.successResult(SuccessCode.SHOW_PROFILE_SUCCESS, userProfileDto);
    }
    /**
     * controller 통해서 member 정보 출력하면 update 된 값으로 잘 출력되는데,
     * H2 DB 에는 회원 정보가 update가 안됨.
     */
    @PostMapping("/update")
    public ResponseEntity<ResponseDto> updateUser(
            @AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody MyPageMemberDto memberDto) {
        Member member = updateMemberServiceImpl.updateMember(userDetails.getId(), memberDto);
        if(member == null) {
            throw new BusinessException(ErrorCode.MEMBER_NOT_FOUND);
        }
        MyProfileDto myProfileDto = MyProfileDto.of(member);
        return responseService.successResult(SuccessCode.UPDATE_SUCCESS, myProfileDto);
    }
}
