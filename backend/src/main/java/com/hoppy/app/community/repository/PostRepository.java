package com.hoppy.app.community.repository;

import com.hoppy.app.community.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author 태경 2022-07-22
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}
