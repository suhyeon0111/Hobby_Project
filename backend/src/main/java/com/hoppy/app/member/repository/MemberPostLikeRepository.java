package com.hoppy.app.member.repository;

import com.hoppy.app.member.domain.MemberPostLike;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 태경 2022-07-22
 */
public interface MemberPostLikeRepository extends JpaRepository<MemberPostLike, Long> {

}
