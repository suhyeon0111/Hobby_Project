package com.hoppy.app.upload.service;

import com.hoppy.app.response.error.exception.BusinessException;
import com.hoppy.app.response.error.exception.ErrorCode;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

@Service
public class UploadServiceImpl implements UploadService {

    @Value("${aws.s3.access-key}")
    private String accessKey;

    @Value("${aws.s3.secret-key}")
    private String secretKey;

    @Override
    public String getPreSignedUrl(String filename) {
        try {
            AwsCredentialsProvider credentialsProvider = StaticCredentialsProvider.create(
                    AwsBasicCredentials.create(accessKey, secretKey));

            S3Presigner presigner = S3Presigner.builder()
                    .region(Region.AP_NORTHEAST_2)
                    .credentialsProvider(credentialsProvider)
                    .build();

            PutObjectRequest objectRequest = PutObjectRequest.builder()
                    .bucket("hoppyservice")
                    .key(filename)
                    .contentType("image/png")
                    .build();

            PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofMinutes(5))
                    .putObjectRequest(objectRequest)
                    .build();

            PresignedPutObjectRequest presignedRequest = presigner
                    .presignPutObject(presignRequest);

            return presignedRequest.url().toString();
            // file url = https://hoppyservice.s3.ap-northeast-2.amazonaws.com/{filename}
        }
        catch (Exception e) {
            throw new BusinessException(ErrorCode.S3_ACCESS_FAIL);
        }
    }
}
