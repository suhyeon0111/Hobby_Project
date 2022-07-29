package com.hoppy.app.upload.service;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

@SpringBootTest
class UploadServiceImplTest {

    void exampleGetPreSignedUrl() {
        /*
        * Sharing objects using preSigned URLs
        * https://docs.aws.amazon.com/AmazonS3/latest/userguide/ShareObjectPreSignedURL.html
        * https://docs.aws.amazon.com/ko_kr/sdk-for-java/latest/developer-guide/examples-s3-presign.html
        * https://sdk.amazonaws.com/java/api/latest/software/amazon/awssdk/services/s3/presigner/S3Presigner.html
        * */
        try {
            AwsCredentialsProvider credentialsProvider = StaticCredentialsProvider.create(
                    AwsBasicCredentials.create("input access key", "input secret key"));

            /*
            * preSigner example code
            * https://www.programcreek.com/java-api-examples/?api=software.amazon.awssdk.services.s3.presigner.S3Presigner
            * */
            S3Presigner presigner = S3Presigner.builder()
                    .region(Region.AP_NORTHEAST_2)
                    .credentialsProvider(credentialsProvider)
                    .build();

            PutObjectRequest objectRequest = PutObjectRequest.builder()
                    .bucket("hoppyservice")
                    .key("tester")
                    .contentType("text/plain")
                    .build();

            PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofMinutes(2))
                    .putObjectRequest(objectRequest)
                    .build();

            PresignedPutObjectRequest presignedRequest = presigner
                    .presignPutObject(presignRequest);


            String myURL = presignedRequest.url().toString();
            System.out.println("Presigned URL to upload a file to: " +myURL);
            System.out.println("Which HTTP method needs to be used when uploading a file: " +
                    presignedRequest.httpRequest().method());

            // Upload content to the Amazon S3 bucket by using this URL
            URL url = presignedRequest.url();

            // Create the connection and use it to upload the new object by using the presigned URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type","text/plain");
            connection.setRequestMethod("PUT");
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
            out.write("This text was uploaded as an object by using a presigned URL.");
            out.close();

            connection.getResponseCode();
            System.out.println("HTTP response code is " + connection.getResponseCode());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}