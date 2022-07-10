package com.hoppy.app.member.repository;

import com.hoppy.app.member.domain.Member;
import com.hoppy.app.login.auth.SocialType;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findBySocialTypeAndSocialId(SocialType socialType, String socialId);

    @Query("select distinct m from Member as m where m.id > :lastId and m.id in :membersId order by m.id desc")
    List<Member> infiniteScrollPagingMember(List<Long> membersId, Long lastId, Pageable pageable);
}
