package com.hoppy.app.community.controller;

import com.hoppy.app.community.dto.CreateReReplyDto;
import com.hoppy.app.community.dto.CreateReplyDto;
import com.hoppy.app.community.dto.UpdateReplyDto;
import com.hoppy.app.community.service.ReplyService;
import com.hoppy.app.login.auth.authentication.CustomUserDetails;
import com.hoppy.app.response.dto.ResponseDto;
import com.hoppy.app.response.service.ResponseService;
import com.hoppy.app.response.service.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @PostMapping
    public ResponseEntity<ResponseDto> createReply(
            @RequestBody CreateReplyDto createReplyDto,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        replyService.createReply(userDetails.getId(), createReplyDto);
        return responseService.ok();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDto> patchReply(
            @PathVariable("id") long id,
            @RequestBody UpdateReplyDto updateReplyDto,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        replyService.updateReply(updateReplyDto, userDetails.getId(), id);
        return responseService.ok();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteReply(
            @PathVariable("id") long id,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        replyService.deleteReply(userDetails.getId(), id);
        return responseService.ok();
    }

    @DeleteMapping("/like/{id}")
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

    @PostMapping("/re")
    public ResponseEntity<ResponseDto> createReReply(
            @RequestBody CreateReReplyDto createReReplyDto,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        replyService.createReReply(userDetails.getId(), createReReplyDto);
        return responseService.ok();
    }

    @PatchMapping("/re/{id}")
    public ResponseEntity<ResponseDto> patchReReply(
            @PathVariable("id") long id,
            @RequestBody UpdateReplyDto updateReplyDto,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        replyService.updateReReply(updateReplyDto, userDetails.getId(), id);
        return responseService.ok();
    }

    @DeleteMapping("/re/{id}")
    public ResponseEntity<ResponseDto> deleteReReply(
            @PathVariable("id") long id,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        replyService.deleteReReply(userDetails.getId(), id);
        return responseService.ok();
    }

    @DeleteMapping("/re/like/{id}")
    public ResponseEntity<ResponseDto> dislikeReReply(
            @PathVariable("id") long id,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        replyService.dislikeReReply(userDetails.getId(), id);
        return responseService.ok();
    }
}
