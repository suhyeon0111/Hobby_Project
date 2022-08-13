package com.hoppy.app.community.repository;

import com.hoppy.app.community.domain.Post;
import com.hoppy.app.meeting.Category;
import com.hoppy.app.meeting.domain.Meeting;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author 태경 2022-07-22
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select distinct p from Post p "
            + "join fetch p.author "
            + "where p.meeting = :meeting and p.id < :lastId "
            + "order by p.id desc")
    List<Post> infiniteScrollPagingPost(@Param("meeting") Meeting meeting, @Param("lastId") Long lastId, Pageable pageable);

    @Query("select p from Post p "
            + "join fetch p.author "
            + "join fetch p.replies as pr "
            + "join fetch pr.author "
            + "join fetch pr.reReplies as prr "
            + "join fetch prr.author "
            + "where p.id = :id")
    Optional<Post> getPostDetail(@Param("id") Long id);

    List<Post> findAllByMeeting(Meeting meeting);
}
