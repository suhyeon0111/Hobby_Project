package com.hoppy.app.upload.service;

public interface UploadService {

    public String getPreSignedUrl(String filename, String contentType);
}
