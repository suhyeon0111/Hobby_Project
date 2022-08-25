package com.hoppy.app.community.controller;

import com.hoppy.app.community.service.ReplyService;
import com.hoppy.app.login.auth.authentication.CustomUserDetails;
import com.hoppy.app.response.dto.ResponseDto;
import com.hoppy.app.response.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * packageName    : com.hoppy.app.community.controller
 * fileName       : ReplyController
 * author         : Kim
 * date           : 2022-08-25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-08-25        Kim       최초 생성
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/reply")
public class ReplyController {

    private final ResponseService responseService;
    private final ReplyService replyService;

    @GetMapping("/like/{id}")
    public ResponseEntity<ResponseDto> likeReply(
            @PathVariable("id") long id,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        replyService.likeReply(userDetails.getId(), id);
        return responseService.ok();
    }
}
