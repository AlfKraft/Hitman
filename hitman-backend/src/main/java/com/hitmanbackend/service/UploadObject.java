package com.hitmanbackend.service;


import com.google.cloud.storage.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;
@Transactional
@Service
public class UploadObject {
    public String uploadObject(MultipartFile file, String firstName, String lastName) throws Exception {

        // The ID of your GCS bucket
        String bucketName = "player_img_bucket";

        // The ID of your GCS object
        String objectName = "%s_%s_profile".formatted(firstName, lastName).toLowerCase();

        // The path to your file to upload
        // String filePath = "path/to/your/file"

        Storage storage = StorageOptions.newBuilder().setProjectId("radiant-precept-396617").build().getService();
        BlobId blobId = BlobId.of(bucketName, objectName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(file.getContentType()).build();
        if (storage.get(bucketName, objectName) == null) {
            Blob blob = storage.create(blobInfo, file.getBytes());
            URL signedUrl = blob.signUrl(7, TimeUnit.DAYS, Storage.SignUrlOption.withV4Signature());
            return String.valueOf(signedUrl);
        }
        else {
            throw new Exception("%s %s image file already exists".formatted(firstName, lastName));
        }

    }

    public String generateNewUrlForPlayer(String firstName, String lastName){
        String bucketName = "player_img_bucket";

        // The ID of your GCS object
        String objectName = "%s_%s_profile".formatted(firstName, lastName).toLowerCase();

        // The path to your file to upload
        // String filePath = "path/to/your/file"

        Storage storage = StorageOptions.newBuilder().setProjectId("radiant-precept-396617").build().getService();
        BlobId blobId = BlobId.of(bucketName, objectName);
        Blob blob = storage.get(blobId);
        if (blob == null){
            return "";
        }
        URL signedUrl = blob.signUrl(7, TimeUnit.DAYS, Storage.SignUrlOption.withV4Signature());
        return String.valueOf(signedUrl);

    }
}