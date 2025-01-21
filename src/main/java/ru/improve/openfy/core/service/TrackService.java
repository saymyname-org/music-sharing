package ru.improve.openfy.core.service;

import ru.improve.openfy.api.dto.upload.UploadTrackRequest;

public interface TrackService {

    void saveTrack(UploadTrackRequest uploadTrackRequest);
}
