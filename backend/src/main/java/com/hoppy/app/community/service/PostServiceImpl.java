package com.hoppy.app.community.service;

import com.hoppy.app.community.domain.Post;
import com.hoppy.app.community.dto.PostDto;
import com.hoppy.app.community.repository.PostRepository;
import com.hoppy.app.like.service.LikeService;
import com.hoppy.app.meeting.domain.Meeting;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.service.MemberService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * @author 태경 2022-08-03
 */
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final int PAGING_COUNT = 8;
    private final PostRepository postRepository;
    private final LikeService likeService;

    @Override
    public List<Post> pagingPostList(Meeting meeting, long lastId) {
        return postRepository.infiniteScrollPagingPost(meeting, lastId, PageRequest.of(0, PAGING_COUNT));
    }

    @Override
    public long getLastId(List<Post> posts) {
        return posts.get(posts.size() - 1).getId() - 1;
    }

    @Override
    public String createNextPagingUrl(long meetingId, long lastId) {
        return "https://hoppy.kro.kr/api/meeting/" + meetingId + "/posts?lastId=" + lastId;
    }

    @Override
    public List<PostDto> listToDtoList(List<Post> posts, long memberId) {
        List<Long> postLikes = likeService.getPostLikes(memberId);
        // 현재 사용자가 "좋아요"를 누른 likedMap 생성
        Map<Long, Boolean> likedMap = postLikes.stream()
                .collect(Collectors.toMap(L -> L, L -> Boolean.TRUE));

        return posts.stream()
                .map(P -> PostDto.postToPostDto(P, likedMap.get(P.getId()), likeService.getPostLikeCount(P.getId())))
                .collect(Collectors.toList());
    }
}
