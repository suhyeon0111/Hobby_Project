package com.hoppy.app.member.repository;

import com.hoppy.app.member.domain.Member;
import com.hoppy.app.login.auth.SocialType;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query("select distinct m from Member m where m.id > :lastId and m.id in :membersId order by m.id desc")
    List<Member> infiniteScrollPagingMember(@Param("membersId") List<Long> membersId, @Param("lastId") Long lastId, Pageable pageable);

    @Query("select distinct m from Member m left join fetch m.postLikes where m.id = :id")
    Optional<Member> findByIdWithPostLikes(@Param("id") Long id);

    @Query("select distinct m from Member m left join fetch m.meetingLikes where m.id = :id")
    Optional<Member> findByIdWithMeetingLikes(@Param("id") Long id);
}
