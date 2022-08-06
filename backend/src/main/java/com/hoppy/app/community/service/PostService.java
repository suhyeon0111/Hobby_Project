package com.hoppy.app.community.service;

import com.hoppy.app.community.domain.Post;
import com.hoppy.app.community.dto.PostDto;
import com.hoppy.app.meeting.domain.Meeting;
import java.util.List;

/**
 * @author 태경 2022-08-02
 */
public interface PostService {

    List<Post> listPostByMeetingWithPaging(Meeting meeting, long lastId);

    long getListsLastPostId(List<Post> posts);

    String createNextPagingUrl(long meetingId, long lastId);

    List<PostDto> postListToPostDtoList(List<Post> posts, long memberId);
}
