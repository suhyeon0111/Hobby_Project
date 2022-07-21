package com.hoppy.app.member.service;

import com.hoppy.app.member.domain.Member;
import com.hoppy.app.member.domain.MemberLike;
import com.hoppy.app.member.repository.MemberLikeRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 태경 2022-07-22
 */
@Service
@RequiredArgsConstructor
public class MemberLikeServiceImpl implements MemberLikeService {

    private final MemberLikeRepository memberLikeRepository;

    @Override
    public MemberLike createMemberLike() {
        return memberLikeRepository.save(MemberLike.builder().build());
    }

    @Override
    public MemberLike getMemberLikeWithMeetingLikes(Member member) {
        Optional<MemberLike> optionalMemberLike = memberLikeRepository.findMemberLikeByMemberWithMeetingLikes(member);

        if(optionalMemberLike.isEmpty()) {
            MemberLike memberLike = createMemberLike();
            member.setMemberLike(memberLike);
            return memberLike;
        }
        return optionalMemberLike.get();
    }


}
