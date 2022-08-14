package com.hoppy.app.community.service;

import com.hoppy.app.community.domain.Post;
import com.hoppy.app.community.domain.Reply;
import com.hoppy.app.community.dto.PostDetailDto;
import com.hoppy.app.community.dto.PostDto;
import com.hoppy.app.community.dto.ReReplyDto;
import com.hoppy.app.community.dto.ReplyDto;
import com.hoppy.app.community.repository.PostRepository;
import com.hoppy.app.like.domain.MemberReReplyLike;
import com.hoppy.app.like.domain.MemberReplyLike;
import com.hoppy.app.meeting.domain.Meeting;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.repository.MemberRepository;
import com.hoppy.app.member.service.MemberService;
import com.hoppy.app.response.error.exception.BusinessException;
import com.hoppy.app.response.error.exception.ErrorCode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 태경 2022-08-03
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl implements PostService {

    private final int PAGING_COUNT = 8;
    private final PostRepository postRepository;
    private final MemberService memberService;

    @Override
    public Post findById(long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if(optionalPost.isEmpty()) {
            throw new BusinessException(ErrorCode.POST_NOT_FOUND);
        }
        return optionalPost.get();
    }

    @Override
    @Transactional
    public List<PostDto> pagingPostListV2(Meeting meeting, long lastId, long memberId) {

        List<Post> posts = postRepository.infiniteScrollPagingPost(meeting, lastId, PageRequest.of(0, PAGING_COUNT));
        if(posts.isEmpty()) {
            throw new BusinessException(ErrorCode.NO_MORE_POST);
        }

        Member member = memberService.findByIdWithPostLikes(memberId);
        Map<Long, Integer> repliesCountMap = new HashMap<>();
        Map<Long, Integer> likesCountMap = new HashMap<>();
        for(var p : posts) {
            int sum = p.getReplies().size();
            for(var r : p.getReplies()) {
                sum += r.getReReplies().size();
            }
            repliesCountMap.put(p.getId(), sum);
            likesCountMap.put(p.getId(), p.getLikes().size());
        }

        Map<Long, Boolean> likedMap = member.getPostLikes().stream()
                .collect(Collectors.toMap(M -> M.getPost().getId(), L -> Boolean.TRUE));

        return posts.stream()
                .map(P -> PostDto.postToPostDto(
                        P,
                        likedMap.containsKey(P.getId()),
                        likesCountMap.get(P.getId()),
                        repliesCountMap.get(P.getId()))
                )
                .collect(Collectors.toList());
    }

    @Override
    public long getLastId(List<PostDto> postDtoList) {
        long lastId = postDtoList.get(postDtoList.size() - 1).getId() - 1;
        if(lastId > 0) return lastId;
        return -1;
    }

    @Override
    public long validCheckLastId(long lastId) {
        if(lastId == 0) {
            return Long.MAX_VALUE;
        }
        else if(lastId < 0) {
            throw new BusinessException(ErrorCode.NO_MORE_POST);
        }
        return lastId;
    }

    @Override
    public String createNextPagingUrl(long meetingId, long lastId) {
        return "https://hoppy.kro.kr/api/meeting/posts?meetingId=" + meetingId + "&lastId=" + lastId;
    }

    @Override
    @Transactional(readOnly = true)
    public PostDetailDto getPostDetailV2(long postId, long memberId) {
        Optional<Post> optPost = postRepository.getPostDetail(postId);
        if(optPost.isEmpty()) {
            throw new BusinessException(ErrorCode.POST_NOT_FOUND);
        }
        Post post = optPost.get();
        Member member = memberService.findByIdWithPostLikes(memberId);

        boolean postLiked = member.getPostLikes().stream().anyMatch(L -> L.getPost().getId() == postId);
        int postLikeCount = post.getLikes().size();

        Map<Long, Boolean> replyLikedMap = member.getReplyLikes()
                .stream().collect(Collectors.toMap(MemberReplyLike::getReplyId, M -> Boolean.TRUE));

        Map<Long, Boolean> reReplyLikedMap = member.getReReplyLikes()
                .stream().collect(Collectors.toMap(MemberReReplyLike::getReReplyId, M -> Boolean.TRUE));

        int replyCountSum = 0;
        List<ReplyDto> replyDtoList = new ArrayList<>();

        for(var reply : post.getReplies()) {
            replyCountSum++;
            long replyId = reply.getId();

            ReplyDto replyDto = ReplyDto.of(reply);
            replyDto.setLiked(replyLikedMap.containsKey(replyId));
            replyDto.setLikeCount(reply.getLikes().size());

            List<ReReplyDto> reReplyDtoList = new ArrayList<>();
            for(var reReply : reply.getReReplies()) {
                replyCountSum++;
                long reReplyId = reReply.getId();

                ReReplyDto reReplyDto = ReReplyDto.of(reReply);
                reReplyDto.setLiked(reReplyLikedMap.containsKey(reReplyId));
                reReplyDto.setLikeCount(reReply.getLikes().size());
                reReplyDtoList.add(reReplyDto);
            }
            replyDto.setReplies(reReplyDtoList);
            replyDtoList.add(replyDto);
        }
        return PostDetailDto.of(post, postLiked, postLikeCount, replyCountSum, replyDtoList);
    }
}
