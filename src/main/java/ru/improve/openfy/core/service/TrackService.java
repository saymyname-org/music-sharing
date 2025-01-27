package ru.improve.openfy.core.service;

import ru.improve.openfy.api.dto.searching.DownloadTrackResponse;
import ru.improve.openfy.api.dto.upload.UploadTrackRequest;

public interface TrackService {

    void uploadTrack(UploadTrackRequest uploadTrackRequest);

    DownloadTrackResponse getTrackDownloadLink(String trackHash);
}
