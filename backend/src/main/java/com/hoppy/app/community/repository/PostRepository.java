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
            + "where p.meeting = :meeting and p.id < :lastId "
            + "order by p.id desc")
    List<Post> infiniteScrollPagingPost(@Param("meeting") Meeting meeting, @Param("lastId") Long lastId, Pageable pageable);

    @Query("select distinct p from Post p "
            + "left join fetch p.replies as pr "
            + "left join fetch pr.reReplies as prr "
            + "where p.id = :id")
    Optional<Post> getPostDetail(@Param("id") Long id);

    @Query("select distinct p from Post p "
            + "left join fetch p.replies as pr "
            + "left join fetch pr.reReplies as prr "
            + "where p.id = :id and p.author.id = :authorId")
    Optional<Post> getPostDetailByIdAndAuthorId(@Param("id") Long id, @Param("authorId") Long authorId);

    @Query("select p from Post p where p.id = :id and p.author.id = :authorId")
    Optional<Post> findByIdAndAuthorId(@Param("id") Long id, @Param("authorId") Long authorId);
}
