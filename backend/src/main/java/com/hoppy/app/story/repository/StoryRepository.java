package com.hoppy.app.story.repository;

import com.hoppy.app.member.domain.Member;
import com.hoppy.app.story.domain.story.Story;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StoryRepository extends JpaRepository<Story, Long> {

    /**
     * 프로필에서 보이는 스토리는 최신순 (storyId 내림차순)으로 3개만 표시
     */
    // TODO: '더보기'등의 버튼 클릭으로 해당 사용자가 작성한 모든 스토리를 조회할 수 있도록 구현
    List<Story> findByMemberIdOrderByIdDesc(Long memberId);
    
    @Query("select s from Story as s join fetch s.member where s.id < :lastId order by s.id desc")
    List<Story> findNextStoryOrderByIdDesc(@Param("lastId") Long lastId, Pageable pageable);
}
