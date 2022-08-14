package com.hoppy.app.like.repository;

import com.hoppy.app.community.domain.Post;
import com.hoppy.app.like.domain.MemberPostLike;
import com.hoppy.app.member.domain.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author 태경 2022-07-22
 */
public interface MemberPostLikeRepository extends JpaRepository<MemberPostLike, Long> {

    Optional<MemberPostLike> findByMemberAndPost(Member member, Post post);

    @Query("select m from MemberPostLike m where m.member.id = :memberId and m.post.id = :postId")
    Optional<MemberPostLike> findByMemberIdAndPostId(
            @Param("memberId") Long memberId,
            @Param("postId") Long postId
    );
}
