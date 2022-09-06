package com.hoppy.app.story.repository;

import com.hoppy.app.story.domain.story.StoryReReply;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StoryReReplyRepository extends JpaRepository<StoryReReply, Long> {

    @Modifying
    @Query("delete from StoryReReply s where s.id in :idList")
    void deleteAllByList(@Param("idList") List<Long> idList);

    @Query("select s from StoryReReply s where s.id = :id and s.member.id = :memberId")
    Optional<StoryReReply> findByIdAndMemberId(
            @Param("id") Long id,
            @Param("memberId") Long memberId
    );
}
