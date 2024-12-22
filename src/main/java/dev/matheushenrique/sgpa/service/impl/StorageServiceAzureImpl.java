package dev.matheushenrique.sgpa.service.impl;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.models.BlobHttpHeaders;
import com.azure.storage.blob.sas.BlobSasPermission;
import com.azure.storage.blob.sas.BlobServiceSasSignatureValues;
import dev.matheushenrique.sgpa.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.OffsetDateTime;

@Service
@ConditionalOnProperty(name = "sgpa.storage.provider", havingValue = "AZURE")
@RequiredArgsConstructor
public class StorageServiceAzureImpl implements StorageService {

    private final BlobContainerClient blobContainerClient;



    @Override
    public String uploadFile(MultipartFile file, String fileName) throws IOException {
        BlobClient blobClient = blobContainerClient.getBlobClient(fileName);
        BlobHttpHeaders headers = new BlobHttpHeaders().setContentType(file.getContentType());
        blobClient.upload(file.getInputStream(), file.getSize());
        blobClient.setHttpHeaders(headers);
        return "Azure Blob Upload Success " + file.getOriginalFilename();
    }

    @Override
    public String getUrlFile(String fileName) {

        BlobClient blobClient = blobContainerClient.getBlobClient(fileName);
        BlobServiceSasSignatureValues values = new BlobServiceSasSignatureValues(
                OffsetDateTime.now().plusHours(1), new BlobSasPermission().setReadPermission(true)
        );
        String sasToken = blobClient.generateSas(values);
        String urlFile = blobClient.getBlobUrl() + "?" + sasToken;
        return urlFile;
    }

    @Override
    public String deleteFile(String fileName) {
        BlobClient blobClient = blobContainerClient.getBlobClient(fileName);
        blobClient.delete();
        return "Azure Blob Delete Success " + fileName;
    }
}
