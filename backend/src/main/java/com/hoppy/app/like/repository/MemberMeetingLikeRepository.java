package com.hoppy.app.like.repository;

import com.hoppy.app.like.domain.LikeManager;
import com.hoppy.app.like.domain.MemberMeetingLike;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberMeetingLikeRepository extends JpaRepository<MemberMeetingLike, Long> {

    Optional<MemberMeetingLike> findMemberMeetingLikeByLikeManagerAndMeetingId(
            LikeManager likeManager, Long meetingId);
}
