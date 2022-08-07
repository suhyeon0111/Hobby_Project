package com.hoppy.app.community.service;

import com.hoppy.app.community.domain.Post;
import com.hoppy.app.community.dto.CountDto;
import com.hoppy.app.community.dto.PostDto;
import com.hoppy.app.community.repository.PostRepository;
import com.hoppy.app.like.domain.MemberPostLike;
import com.hoppy.app.like.service.LikeManagerService;
import com.hoppy.app.meeting.domain.Meeting;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.service.MemberService;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

    private final PostRepository postRepository;
    private final MemberService memberService;
    private final LikeManagerService likeManagerService;

    @Override
    public List<Post> pagingPostList(Meeting meeting, long lastId) {
        return postRepository.infiniteScrollPagingPost(meeting, lastId, PageRequest.of(0, 8));
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
        // TODO: 2022.08.06. 메서드 성능 검증이 필요함 -tae
        // 사용자가 "좋아요"를 눌렀는지 확인하기 위한 likedMap 생성
        Member member = memberService.findById(memberId);
        Set<MemberPostLike> postLikes = likeManagerService.getPostLikes(member);
        Map<Long, Boolean> likedMap = postLikes.stream()
                .map(MemberPostLike::getPostId)
                .collect(Collectors.toMap(L -> L, L -> Boolean.TRUE));

        Map<Long, Integer> likeCountMap = posts.stream()
                .map(P -> likeManagerService.getLikeCount(P.getId()))
                .collect(Collectors.toMap(CountDto::getId, CountDto::getCount));

        Map<Long, Integer> replyCountMap = posts.stream()
                .map(P -> CountDto.of(P.getId(), P.getReplies().size()))
                .collect(Collectors.toMap(CountDto::getId, CountDto::getCount));

        return posts.stream()
                .map(P -> PostDto.postToPostDto(P, likedMap.get(P.getId()), likeCountMap.get(P.getId()), replyCountMap.get(P.getId())))
                .collect(Collectors.toList());
    }
}
