package com.hoppy.app.meeting.service;

import com.hoppy.app.meeting.Category;

/*
* 모임 조회 서비스
* 1. 카테고리에 속하는 모임 조회
* 2. 제목 검색 결과에 해당하는 모임 조회
* 3. ...
* */
public interface MeetingInquiryService {

    public void listMeetingByCategory(Category category);
}
