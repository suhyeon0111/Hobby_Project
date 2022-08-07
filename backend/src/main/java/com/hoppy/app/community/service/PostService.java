package com.hoppy.app.community.service;

import com.hoppy.app.community.domain.Post;
import com.hoppy.app.community.dto.PostDto;
import com.hoppy.app.meeting.domain.Meeting;
import java.util.List;

/**
 * @author 태경 2022-08-02
 */
public interface PostService {

    List<Post> pagingPostList(Meeting meeting, long lastId);

    long getLastId(List<Post> posts);

    String createNextPagingUrl(long meetingId, long lastId);

    List<PostDto> listToDtoList(List<Post> posts, long memberId);
}
