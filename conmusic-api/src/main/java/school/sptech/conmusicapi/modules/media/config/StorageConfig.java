package school.sptech.conmusicapi.modules.media.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfig {

    AmazonS3 amazonS3Client = AmazonS3Client.builder()
            .withRegion(Regions.US_EAST_1)
            .build();

    @Bean
    public AmazonS3 amazonS3Client() {
        return amazonS3Client;
    }
}
