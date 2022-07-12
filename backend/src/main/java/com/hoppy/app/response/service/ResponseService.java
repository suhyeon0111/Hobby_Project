package com.hoppy.app.response.service;

import com.hoppy.app.member.dto.MyProfileDto;
import com.hoppy.app.response.dto.ResponseDto;
import org.springframework.http.ResponseEntity;

public interface ResponseService {
    public ResponseEntity<ResponseDto> successResult(SuccessCode code);
    public ResponseEntity<ResponseDto> successResult(SuccessCode code, Object body);
}