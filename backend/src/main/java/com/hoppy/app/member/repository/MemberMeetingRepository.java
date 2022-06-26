package com.hoppy.app.member.repository;

import com.hoppy.app.member.domain.MemberMeeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberMeetingRepository extends JpaRepository<MemberMeeting, Long> {

}
