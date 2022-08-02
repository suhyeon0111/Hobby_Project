package com.hoppy.app.story.repository;

import com.hoppy.app.story.domain.story.Story;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoryRepository extends JpaRepository<Story, Long> {

}
