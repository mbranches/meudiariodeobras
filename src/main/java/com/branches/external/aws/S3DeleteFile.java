package com.branches.external.aws;

import com.branches.exception.InternalServerError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;

@Slf4j
@Component
public class S3DeleteFile {
    @Value("${aws.accessKeyId}")
    private String accessKeyId;
    @Value("${aws.secretAccessKey}")
    private String secretAccessKey;
    @Value("${aws.s3.bucketName}")
    private String bucketName;

    public void execute(String fileUrl) {
        AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(accessKeyId, secretAccessKey);

        try (S3Client s3Client = S3Client.builder()
                .region(Region.US_EAST_2)
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                .build()) {
            log.info("Deletando arquivo do S3: {}", fileUrl);

            String key = extractKeyFromUrl(fileUrl);

            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            s3Client.deleteObject(deleteObjectRequest);

            log.info("Arquivo deletado com sucesso do S3: {}", fileUrl);
        }
    }

    private String extractKeyFromUrl(String fileUrl) {
        String s3Domain = "https://%s.s3.us-east-2.amazonaws.com/".formatted(bucketName);
        if (!fileUrl.startsWith(s3Domain)) {
            throw new InternalServerError("Ocorreu um erro interno ao deletar o arquivo do S3: URL inválida");
        }

        return fileUrl.substring(s3Domain.length());

    }
}