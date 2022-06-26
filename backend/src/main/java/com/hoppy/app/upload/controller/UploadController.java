package com.hoppy.app.upload.controller;

import com.hoppy.app.response.service.SuccessCode;
import com.hoppy.app.upload.dto.PresignedDto;
import com.hoppy.app.upload.service.UploadService;
import com.hoppy.app.response.dto.ResponseDto;
import com.hoppy.app.response.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/upload")
public class UploadController {

    private final UploadService uploadService;
    private final ResponseService responseService;

    @GetMapping("/presigned/{filename}")
    public ResponseEntity<ResponseDto> getPreSignedURL(@PathVariable("filename") String filename) {

        String url = uploadService.getPreSignedUrl(filename);

        return responseService.successResult(
                SuccessCode.GET_PRESIGNED_URL_SUCCESS,
                new PresignedDto(url)
        );
    }
}
