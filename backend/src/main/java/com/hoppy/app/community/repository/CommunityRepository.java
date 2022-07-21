package com.hoppy.app.community.repository;

import com.hoppy.app.community.domain.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author 태경 2022-07-22
 */
@Repository
public interface CommunityRepository extends JpaRepository<Community, Long> {

}
