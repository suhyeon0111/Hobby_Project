package com.hoppy.app.community.controller;

import com.hoppy.app.community.service.ReplyService;
import com.hoppy.app.login.auth.authentication.CustomUserDetails;
import com.hoppy.app.response.dto.ResponseDto;
import com.hoppy.app.response.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
 * 2022-08-28        Kim       댓글, 대댓글 좋아요 기능 추가
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

    @DeleteMapping("/dislike/{id}")
    public ResponseEntity<ResponseDto> dislikeReply(
            @PathVariable("id") long id,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        replyService.dislikeReply(userDetails.getId(), id);
        return responseService.ok();
    }

    @GetMapping("/re/like/{id}")
    public ResponseEntity<ResponseDto> likeReReply(
            @PathVariable("id") long id,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        replyService.likeReReply(userDetails.getId(), id);
        return responseService.ok();
    }

    @DeleteMapping("/re/dislike/{id}")
    public ResponseEntity<ResponseDto> dislikeReReply(
            @PathVariable("id") long id,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        replyService.dislikeReReply(userDetails.getId(), id);
        return responseService.ok();
    }
}
