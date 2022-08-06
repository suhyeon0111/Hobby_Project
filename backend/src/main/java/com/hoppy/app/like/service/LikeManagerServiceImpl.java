package com.hoppy.app.like.service;

import com.hoppy.app.community.dto.CountDto;
import com.hoppy.app.like.repository.MemberPostLikeRepository;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.like.domain.LikeManager;
import com.hoppy.app.like.repository.LikeManagerRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

/**
 * @author 태경 2022-07-22
 */
@Service
@RequiredArgsConstructor
public class LikeManagerServiceImpl implements LikeManagerService {

    private final LikeManagerRepository likeManagerRepository;
    private final MemberPostLikeRepository memberPostLikeRepository;

    @Override
    public LikeManager createLikeManager() {
        return likeManagerRepository.save(LikeManager.builder().build());
    }

    @Override
    public LikeManager getMemberMeetingLikes(Member member) {
        Optional<LikeManager> optionalMemberLike = likeManagerRepository.findLikeManagerWithMeetingLikesByMember(member);

        if(optionalMemberLike.isEmpty()) {
            LikeManager likeManager = createLikeManager();
            member.setLikeManager(likeManager);
            return likeManager;
        }
        return optionalMemberLike.get();
    }

    @Override
    public LikeManager getMemberPostLikes(Member member) {
        Optional<LikeManager> optionalLikeManager = likeManagerRepository.findLikeManagerWithPostLikesByMember(member);

        if(optionalLikeManager.isEmpty()) {
            LikeManager likeManager = createLikeManager();
            member.setLikeManager(likeManager);
            return likeManager;
        }
        return optionalLikeManager.get();
    }

    @Override
    public CountDto getPostsLikeCount(Long postId) {
        return CountDto.of(postId, memberPostLikeRepository.findAllByPostId(postId).size());
    }

}
