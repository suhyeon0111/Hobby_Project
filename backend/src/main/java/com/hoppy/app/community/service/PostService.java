package com.hoppy.app.community.service;

import com.hoppy.app.community.domain.Post;
import com.hoppy.app.community.dto.PostDetailDto;
import com.hoppy.app.community.dto.PostDto;
import com.hoppy.app.meeting.domain.Meeting;
import java.util.List;

/**
 * @author 태경 2022-08-02
 */
public interface PostService {

    public Post findById(long id);

    public List<PostDto> pagingPostListV1(Meeting meeting, long lastId, long memberId);

    public List<PostDto> pagingPostListV2(Meeting meeting, long lastId, long memberId);

    public long getLastId(List<PostDto> posts);

    public long validCheckLastId(long lastId);

    public String createNextPagingUrl(long meetingId, long lastId);

    public PostDetailDto getPostDetailV1(long postId, long memberId);
}
