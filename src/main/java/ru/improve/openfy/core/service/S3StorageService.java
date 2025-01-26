package ru.improve.openfy.core.service;

import ru.improve.openfy.api.dto.upload.UploadTrackRequest;

import java.io.IOException;

public interface S3StorageService {

    void uploadTrackInStorage(UploadTrackRequest uploadTrackRequest, String fileHash, String bucket) throws IOException;

    String getFileLink(String key, String bucket);
}
