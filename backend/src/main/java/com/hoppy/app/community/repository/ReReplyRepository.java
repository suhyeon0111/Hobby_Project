package com.hoppy.app.community.repository;

import com.hoppy.app.community.domain.ReReply;
import com.hoppy.app.community.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author 태경 2022-08-10
 */
public interface ReReplyRepository extends JpaRepository<ReReply, Long> {

    @Modifying
    @Query("delete from ReReply r where r.id in :idList")
    void deleteAllByList(@Param("idList") List<Long> idList);

    @Query("select r from ReReply r where r.id = :id and r.author.id = :authorId")
    Optional<ReReply> findByIdAndAuthorId(@Param("id") Long id, @Param("authorId") Long authorId);
}
