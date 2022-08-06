package com.hoppy.app.like.repository;

import com.hoppy.app.community.domain.Post;
import com.hoppy.app.like.domain.MemberPostLike;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 태경 2022-07-22
 */
public interface MemberPostLikeRepository extends JpaRepository<MemberPostLike, Long> {

    List<MemberPostLike> findAllByPostId(Long postId);
}
