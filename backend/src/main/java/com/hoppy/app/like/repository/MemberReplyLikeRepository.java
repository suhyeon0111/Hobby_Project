package com.hoppy.app.like.repository;

import com.hoppy.app.like.domain.MemberReplyLike;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 태경 2022-07-22
 */
public interface MemberReplyLikeRepository extends JpaRepository<MemberReplyLike, Long> {

}
