package ru.improve.openfy.core.service.imp;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.improve.openfy.api.dto.upload.UploadTrackRequest;
import ru.improve.openfy.core.configuration.storage.S3StorageConfigData;
import ru.improve.openfy.core.service.S3ClientService;

@RequiredArgsConstructor
@Slf4j
@Service
public class S3ClientServiceImp implements S3ClientService {

    private final S3StorageConfigData s3StorageConfigData;

    @Value("${openfy.s3.access_key}")
    private String s3AccessKey;

    @Value("${openfy.s3.private_key}")
    private String s3SecretKey;

    @Override
    public void saveTrack(UploadTrackRequest uploadTrackRequest) {
        MultipartFile file = uploadTrackRequest.getFile();

        AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard()
                    .withEndpointConfiguration(
                            new AwsClientBuilder.EndpointConfiguration(
                                    s3StorageConfigData.getServiceEndpoint(), s3StorageConfigData.getSigningRegion()
                            )
                    )
                    .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(s3AccessKey, s3SecretKey)))
                    .build();

        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getBytes().length);
            amazonS3.putObject(s3StorageConfigData.getMusicBucketName(), uploadTrackRequest.getTrackName(), file.getInputStream(), metadata);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
