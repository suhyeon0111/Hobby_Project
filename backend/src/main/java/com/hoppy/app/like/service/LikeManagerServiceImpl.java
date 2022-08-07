package com.hoppy.app.like.service;

import com.hoppy.app.community.dto.CountDto;
import com.hoppy.app.like.domain.MemberMeetingLike;
import com.hoppy.app.like.domain.MemberPostLike;
import com.hoppy.app.like.repository.MemberPostLikeRepository;
import com.hoppy.app.member.domain.Member;
import com.hoppy.app.like.domain.LikeManager;
import com.hoppy.app.like.repository.LikeManagerRepository;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
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
    public Set<MemberMeetingLike> getMeetingLikes(Member member) {
        Optional<LikeManager> optionalLikeManager = likeManagerRepository.findLikeManagerWithMeetingLikesByMember(member);

        if(optionalLikeManager.isEmpty()) {
            LikeManager likeManager = createLikeManager();
            member.setLikeManager(likeManager);
            return new HashSet<>();
        }
        return optionalLikeManager.get().getMeetingLikes();
    }

    @Override
    public Set<MemberPostLike> getPostLikes(Member member) {
        Optional<LikeManager> optionalLikeManager = likeManagerRepository.findLikeManagerWithPostLikesByMember(member);

        if(optionalLikeManager.isEmpty()) {
            LikeManager likeManager = createLikeManager();
            member.setLikeManager(likeManager);
            return new HashSet<>();
        }
        return optionalLikeManager.get().getPostLikes();
    }

    @Override
    public CountDto getLikeCount(Long postId) {
        return CountDto.of(postId, memberPostLikeRepository.findAllByPostId(postId).size());
    }

}
