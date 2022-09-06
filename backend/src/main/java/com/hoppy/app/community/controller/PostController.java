package com.hoppy.app.community.controller;

import com.hoppy.app.community.dto.CreatePostDto;
import com.hoppy.app.community.dto.PostDetailDto;
import com.hoppy.app.community.service.PostService;
import com.hoppy.app.login.auth.authentication.CustomUserDetails;
import com.hoppy.app.response.dto.ResponseDto;
import com.hoppy.app.response.service.ResponseService;
import com.hoppy.app.response.service.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * @author 태경 2022-08-09
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final ResponseService responseService;
    private final PostService postService;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> getPostDetail(
            @PathVariable("id") long id,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        PostDetailDto dto = postService.getPostDetailV2(id, userDetails.getId());
        return responseService.successResult(SuccessCode.GET_POST_DETAIL_SUCCESS, dto);
    }

    @PostMapping
    public ResponseEntity<ResponseDto> createPost(
            @RequestBody CreatePostDto createPostDto,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        postService.createPost(createPostDto, userDetails.getId());
        return responseService.successResult(SuccessCode.CREATE_POST_SUCCESS);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deletePost(
            @PathVariable("id") long id,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        postService.deletePost(userDetails.getId(), id);
        return responseService.successResult(SuccessCode.DELETE_POST_SUCCESS);
    }

    @GetMapping("/like/{id}")
    public ResponseEntity<ResponseDto> likePost(
            @PathVariable("id") long id,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        postService.likePost(userDetails.getId(), id);
        return responseService.ok();
    }

    @DeleteMapping("/like/{id}")
    public ResponseEntity<ResponseDto> dislikePost(
            @PathVariable("id") long id,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        postService.dislikePost(userDetails.getId(), id);
        return responseService.ok();
    }
}
