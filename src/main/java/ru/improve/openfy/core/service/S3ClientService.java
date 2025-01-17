package ru.improve.openfy.core.service;

import ru.improve.openfy.api.dto.upload.UploadTrackRequest;

public interface S3ClientService {

    void saveTrack(UploadTrackRequest uploadTrackRequest);
}
