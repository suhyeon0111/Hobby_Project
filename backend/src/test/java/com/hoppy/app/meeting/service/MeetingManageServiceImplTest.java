package com.hoppy.app.meeting.service;

import static org.junit.jupiter.api.Assertions.*;

import com.hoppy.app.meeting.Category;
import com.hoppy.app.meeting.domain.Meeting;
import com.hoppy.app.meeting.dto.CreateMeetingDto;
import com.hoppy.app.meeting.repository.MeetingRepository;
import javax.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MeetingManageServiceImplTest {

    @Autowired
    MeetingManageService meetingManageService;

    @DisplayName("모임이 잘 생성되는지 확인")
    @Test
    void createMeeting() {

        CreateMeetingDto createMeetingDto = CreateMeetingDto.builder()
                .category(1)
                .content("testContent")
                .title("testTitle")
                .url("test")
                .memberLimit(10)
                .build();

        Meeting meeting = meetingManageService.createMeeting(createMeetingDto);
        Assertions.assertThat(meeting.getCategory()).isEqualByComparingTo(Category.HEALTH);
    }
}