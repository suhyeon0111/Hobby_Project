package com.hoppy.app.meeting.repository;

import com.hoppy.app.meeting.common.Category;
import com.hoppy.app.meeting.domain.Meeting;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {

//    @Query("select distinct m from Meeting as m join fetch m.participants join fetch m.myMeetingLikes")
    @Query("select distinct m from Meeting as m left join m.participants left join m.myMeetingLikes where m.category = :category")
    List<Meeting> findAllMeetingByCategoryUsingFetch(@Param("category") Category category);

    Integer countMeetingByTitle(String title);
}
