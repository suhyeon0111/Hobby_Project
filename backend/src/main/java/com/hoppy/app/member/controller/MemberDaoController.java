package com.hoppy.app.member.controller;

import com.hoppy.app.login.auth.authentication.CustomUserDetails;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.dto.UpdateMemberDto;
import com.hoppy.app.member.repository.MemberRepository;
import com.hoppy.app.member.service.MemberService;
import com.hoppy.app.member.service.MemberServiceImpl;
import com.hoppy.app.response.dto.ResponseDto;
import com.hoppy.app.response.error.exception.BusinessException;
import com.hoppy.app.response.error.exception.ErrorCode;
import com.hoppy.app.response.service.ResponseService;
import com.hoppy.app.response.service.SuccessCode;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberDaoController {

    private final MemberService memberService;
    private final ResponseService responseService;
    @DeleteMapping("/member")
    public ResponseEntity<ResponseDto> deleteUser(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long memberId = customUserDetails.getId();
        memberService.deleteById(memberId);
        return responseService.successResult(SuccessCode.DELETE_SUCCESS);
    }
}
