package ru.improve.openfy.core.service;

import ru.improve.openfy.api.dto.track.DownloadTrackResponse;
import ru.improve.openfy.api.dto.track.SelectTrackRequest;
import ru.improve.openfy.api.dto.track.SelectTrackResponse;
import ru.improve.openfy.api.dto.track.UploadTrackRequest;
import ru.improve.openfy.api.dto.track.UploadTrackResponse;

import java.util.List;

public interface TrackService {

    List<SelectTrackResponse> getAllTracks(int pageNumber, int itemsPerPage);

    List<SelectTrackResponse> getTracksWithParameters(SelectTrackRequest selectTrackRequest);

    UploadTrackResponse uploadTrack(UploadTrackRequest uploadTrackRequest);

    DownloadTrackResponse getTrackDownloadLink(String trackHash);
}
