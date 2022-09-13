package com.hoppy.app.community.repository;

import com.hoppy.app.community.domain.Post;
import com.hoppy.app.community.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author 태경 2022-07-22
 */
@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {

    @Modifying
    @Query("delete from Reply r where r.id in :idList")
    void deleteAllByList(@Param("idList") List<Long> idList);

    @Query("select r from Reply r where r.id = :id and r.author.id = :authorId")
    Optional<Reply> findByIdAndAuthorId(@Param("id") Long id, @Param("authorId") Long authorId);

    @Query("select r from Reply r " +
            "left join fetch r.reReplies " +
            "where r.id = :id and r.author.id = :authorId")
    Optional<Reply> findByIdAndAuthorIdWithReReplies(@Param("id") Long id, @Param("authorId") Long authorId);
}
