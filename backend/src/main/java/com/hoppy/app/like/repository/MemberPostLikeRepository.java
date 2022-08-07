package com.hoppy.app.like.repository;

import com.hoppy.app.like.domain.MemberPostLike;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 태경 2022-07-22
 */
public interface MemberPostLikeRepository extends JpaRepository<MemberPostLike, Long> {

    Optional<MemberPostLike> findByMemberIdAndPostId(Long memberId, Long postId);
    List<MemberPostLike> findAllByMemberId(Long memberId);
    List<MemberPostLike> findAllByPostId(Long postId);
}
