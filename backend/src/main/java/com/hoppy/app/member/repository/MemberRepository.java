package com.hoppy.app.member.repository;

import com.hoppy.app.member.domain.Member;
import com.hoppy.app.login.oauth.SocialType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findBySocialTypeAndSocialId(SocialType socialType, String socialId);
}
