package com.hoppy.app.member.repository;

import com.hoppy.app.member.domain.MemberMeetingLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberMeetingLikeRepository extends JpaRepository<MemberMeetingLike, Long> {

}
