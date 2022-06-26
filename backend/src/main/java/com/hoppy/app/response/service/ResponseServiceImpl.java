package com.hoppy.app.response.service;

import com.hoppy.app.response.code.SuccessCode;
import com.hoppy.app.response.dto.Data;
import com.hoppy.app.response.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResponseServiceImpl implements ResponseService {

    @Override
    public ResponseEntity<ResponseDto> successResult(SuccessCode code) {
        return new ResponseEntity<>(ResponseDto.commonResponse(code.getStatus(), code.getMessage()),
                HttpStatus.valueOf(code.getStatus()));
    }

    @Override
    public ResponseEntity<ResponseDto> successResult(SuccessCode code, Data body) {
        return new ResponseEntity<>(ResponseDto.commonResponse(code.getStatus(), code.getMessage(),
                body),HttpStatus.valueOf(code.getStatus()));
    }
}
