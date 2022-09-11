package com.hoppy.app.community.service;

import com.hoppy.app.community.domain.Post;
import com.hoppy.app.community.dto.CreatePostDto;
import com.hoppy.app.community.dto.PostDetailDto;
import com.hoppy.app.community.dto.PostDto;
import com.hoppy.app.community.dto.UpdatePostDto;
import com.hoppy.app.meeting.domain.Meeting;
import java.util.List;

/**
 * @author 태경 2022-08-02
 */
public interface PostService {

    Post findById(long id);

    void createPost(CreatePostDto createPostDto, long memberId);

    void updatePost(UpdatePostDto updatePostDto, long memberId, long postId);

    void deletePost(long memberId, long postId);

    void likePost(long memberId, long postId);

    void dislikePost(long memberId, long postId);

    List<PostDto> pagingPostListV2(Meeting meeting, long lastId, long memberId);

    long getLastId(List<PostDto> posts);

    long validCheckLastId(long lastId);

    String createNextPagingUrl(long meetingId, long lastId);

    PostDetailDto getPostDetailV2(long postId, long memberId);
}
