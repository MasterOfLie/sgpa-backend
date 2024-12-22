package dev.matheushenrique.sgpa.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import java.net.URI;

@Slf4j
@Component
@ConditionalOnProperty(name = "sgpa.storage.provider", havingValue = "S3")
public class UploadConfigS3 {

    @Value("${sgpa.upload.service}")
    private String serviceType;
    @Value("${sgpa.upload.config.s3.region.name:sgpa}")
    private String regionName;
    @Value("${sgpa.upload.config.s3.endpoint:sgpa.local.hot}")
    private String endpoint;
    @Value("${sgpa.upload.config.s3.accesskey}")
    private String accessKey;
    @Value("${sgpa.upload.config.s3.secretkey}")
    private String secretKey;

    @Bean
    private S3Client s3Client(){
        if (serviceType.equalsIgnoreCase("R2")) {
            log.info("Configuring " +serviceType +" S3 client");
            return S3Client.builder()
                    .endpointOverride(URI.create(endpoint))
                    .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)))
                    .region(Region.of(regionName))
                    .serviceConfiguration(S3Configuration.builder().pathStyleAccessEnabled(true).build())
                    .build();

        } else if (serviceType.equalsIgnoreCase("AWS")) {
            log.info("Configuring AWS S3 client");
              return S3Client.builder()
                      .region(Region.of(regionName))
                      .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey))).build();
        } else {
            log.warn("Tipo de serviço inválido fornecido: " + serviceType
                    + ". Opções válidas são: 'AWS' para Amazon S3, 'R2' para Cloudflare, 'MINIO' para servidor auto-hospedado MinIO, ou 'SUPABASE' para Supabase.");
            throw new IllegalArgumentException("Tipo de serviço inválido: " + serviceType
                    + ". Por favor, forneça um dos seguintes: 'AWS', 'R2', 'MINIO' ou 'SUPABASE'.");
        }
    }
    @Bean
    public S3Presigner s3Presigner() {
        if (serviceType.equalsIgnoreCase("R2") || serviceType.equalsIgnoreCase("MINIO") || serviceType.equalsIgnoreCase("SUPABASE")) {
            return S3Presigner.builder()
                    .region(Region.of(regionName))
                    .endpointOverride(URI.create(endpoint))
                    .serviceConfiguration(S3Configuration.builder().pathStyleAccessEnabled(true).build())
                    .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)))
                    .build();

        } else if (serviceType.equalsIgnoreCase("AWS")) {
            return S3Presigner.builder()
                    .region(Region.of(regionName))
                    .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)))
                    .build();
        } else {
            throw new IllegalArgumentException("Tipo de serviço inválido: " + serviceType
                    + ". Por favor, forneça um dos seguintes: 'AWS', 'R2', 'MINIO' ou 'SUPABASE'.");
        }
    }

}
