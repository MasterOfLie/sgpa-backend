package dev.matheushenrique.sgpa.config;

import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import com.azure.storage.blob.*;



@Slf4j
@Component
@ConditionalOnProperty(name = "sgpa.storage.provider", havingValue = "AZURE")
public class UploadConfigAzure {

    @Value("${sgpa.upload.config.azure.connectio-string:sgpa.local.host}")
    private String connectionString;
    @Value("${sgpa.upload.config.azure.container-name:sgpa}")
    private String containerName;

    @Bean public BlobContainerClient blobContainerClient() {
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                .connectionString(connectionString)
                .buildClient();
        log.info("Connected to Azure Blob Service");
        return blobServiceClient.getBlobContainerClient(containerName);
    }

}
