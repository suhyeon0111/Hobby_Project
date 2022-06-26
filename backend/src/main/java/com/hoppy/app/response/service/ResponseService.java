package com.hoppy.app.response.service;

import com.hoppy.app.response.code.SuccessCode;
import com.hoppy.app.response.dto.Data;
import com.hoppy.app.response.dto.ResponseDto;
import org.springframework.http.ResponseEntity;

public interface ResponseService {
    public ResponseEntity<ResponseDto> successResult(SuccessCode code);
    public ResponseEntity<ResponseDto> successResult(SuccessCode code, Data body);
}