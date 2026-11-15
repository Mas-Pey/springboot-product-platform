package com.project.product_service.util;

import com.project.product_service.exception.FileStorageException;
import io.minio.BucketExistsArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MinioUtil {
    private final MinioClient minioClient;

    @Value("${minio.bucket.name}")
    private String bucketName;

    @Value("${minio.presigned-expiry-minutes}")
    private int presignedExpiryMinutes;

    private void ensureBucketExists() throws Exception {
        boolean exists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        if (!exists) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            log.info("Bucket '{}' berhasil dibuat di MinIO", bucketName);
        }
    }

    public void upload(String fileName, byte[] content, String contentType) {
        try {
            ensureBucketExists();
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .stream(new ByteArrayInputStream(content), content.length, -1)
                    .contentType(contentType)
                    .build());
            log.info("Upload file '{}' ke bucket '{}' berhasil", fileName, bucketName);
        } catch (Exception e) {
            throw new FileStorageException("Gagal upload ke MinIO: " + e.getMessage());
        }
    }

    public String getDownloadUrl(String fileName) {
        try {
            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .method(Method.GET)
                    .bucket(bucketName)
                    .object(fileName)
                    .expiry(presignedExpiryMinutes, TimeUnit.MINUTES)
                    .build());
        } catch (Exception e) {
            throw new FileStorageException("Gagal membuat link download: " + e.getMessage());
        }
    }
}