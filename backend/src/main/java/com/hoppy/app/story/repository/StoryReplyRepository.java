package com.hoppy.app.story.repository;

import com.hoppy.app.story.domain.story.StoryReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoryReplyRepository extends JpaRepository<StoryReply, Long> {

}
