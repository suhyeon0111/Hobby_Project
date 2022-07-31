package com.hoppy.app.meeting.service;

import static org.junit.jupiter.api.Assertions.*;

import com.hoppy.app.meeting.Category;
import com.hoppy.app.meeting.domain.Meeting;
import com.hoppy.app.member.domain.MemberMeeting;
import com.hoppy.app.member.repository.MemberMeetingRepository;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * @author 태경 2022-07-29
 */
@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_METHOD)
class MeetingManageServiceImplTest {

    @InjectMocks
    MeetingManageService meetingManageService;

    @Mock
    MemberMeetingRepository memberMeetingRepository;

    void saveMeeting() {
    }

    void createAndSaveMemberMeetingData() {
    }

    void createMeeting() {
    }

    void checkTitleDuplicate() {
    }

}