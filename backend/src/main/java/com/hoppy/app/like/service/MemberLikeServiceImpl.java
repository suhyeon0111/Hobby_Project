package com.hoppy.app.like.service;

import com.hoppy.app.member.domain.Member;
import com.hoppy.app.like.domain.LikeManager;
import com.hoppy.app.like.repository.LikeManagerRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 태경 2022-07-22
 */
@Service
@RequiredArgsConstructor
public class MemberLikeServiceImpl implements MemberLikeService {

    private final LikeManagerRepository likeManagerRepository;

    @Override
    public LikeManager createMemberLike() {
        return likeManagerRepository.save(LikeManager.builder().build());
    }

    @Override
    public LikeManager getMemberLikeWithMeetingLikes(Member member) {
        Optional<LikeManager> optionalMemberLike = likeManagerRepository.findMemberLikeAndMeetingLikesByMember(member);

        if(optionalMemberLike.isEmpty()) {
            LikeManager likeManager = createMemberLike();
            member.setLikeManager(likeManager);
            return likeManager;
        }
        return optionalMemberLike.get();
    }


}
