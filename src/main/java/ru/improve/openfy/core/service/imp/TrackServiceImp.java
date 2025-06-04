package ru.improve.openfy.core.service.imp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.improve.openfy.api.dto.track.DownloadTrackResponse;
import ru.improve.openfy.api.dto.track.SelectTrackRequest;
import ru.improve.openfy.api.dto.track.SelectTrackResponse;
import ru.improve.openfy.api.dto.track.UploadTrackRequest;
import ru.improve.openfy.api.dto.track.UploadTrackResponse;
import ru.improve.openfy.api.error.ServiceException;
import ru.improve.openfy.core.configuration.EntitySelectLimits;
import ru.improve.openfy.core.configuration.YandexStorageConfigData;
import ru.improve.openfy.core.mappers.TrackMapper;
import ru.improve.openfy.core.models.Album;
import ru.improve.openfy.core.models.Artist;
import ru.improve.openfy.core.models.Track;
import ru.improve.openfy.core.security.UserPrincipal;
import ru.improve.openfy.core.service.AlbumService;
import ru.improve.openfy.core.service.ArtistService;
import ru.improve.openfy.core.service.S3StorageService;
import ru.improve.openfy.core.service.TrackService;
import ru.improve.openfy.core.track.enums.MusicFormat;
import ru.improve.openfy.repositories.TrackRepository;
import ru.improve.openfy.util.EnumMapper;
import ru.improve.openfy.util.FileHashCalculator;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import static ru.improve.openfy.api.error.ErrorCode.ALREADY_EXIST;
import static ru.improve.openfy.api.error.ErrorCode.INTERNAL_SERVER_ERROR;
import static ru.improve.openfy.core.validators.Validators.checkNotBlank;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrackServiceImp implements TrackService {

    private final TrackRepository trackRepository;

    private final ArtistService artistService;

    private final AlbumService albumService;

    private final EntitySelectLimits selectLimits;

    private final TrackMapper trackMapper;

    private final S3StorageService s3StorageService;

    private final YandexStorageConfigData yandexStorageConfigData;

    @Transactional
    @Override
    public List<SelectTrackResponse> getAllTracks(int pageNumber, int itemsPerPage) {
        if (itemsPerPage > selectLimits.getTracksPerPage()) {
            itemsPerPage = selectLimits.getTracksPerPage();
        }

        Pageable page = PageRequest.of(
                pageNumber,
                itemsPerPage,
                Sort.by("name"));

        Page<Track> tracks = trackRepository.findAll(page);
        return trackStreamToSelectTrackResponseListMap(tracks.stream());
    }

    @Transactional
    @Override
    public List<SelectTrackResponse> getTracksWithParameters(SelectTrackRequest selectTrackRequest) {
        int itemsPerPage = selectTrackRequest.getItemsPerPage();
        if (itemsPerPage > selectLimits.getTracksPerPage()) {
            itemsPerPage = selectLimits.getTracksPerPage();
        }

        Pageable page = PageRequest.of(
                selectTrackRequest.getPageNumber(),
                itemsPerPage,
                Sort.by("name"));

        List<Track> tracks = trackRepository.findAllByNameContainingIgnoreCase(selectTrackRequest.getName(), page);
        return trackStreamToSelectTrackResponseListMap(tracks.stream());
    }

    @Transactional
    @Override
    public UploadTrackResponse uploadTrack(UploadTrackRequest uploadTrackRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();

        try {
            String fileHash = FileHashCalculator.getHashFromFileInputString(
                    uploadTrackRequest.getFile().getInputStream());

            MusicFormat musicFormat = EnumMapper.enumFromString(
                    uploadTrackRequest.getMusicFormat(), MusicFormat.class, "music format");

            Artist artist = artistService.getArtistById(uploadTrackRequest.getArtistId());
            Album album = albumService.getAlbumById(uploadTrackRequest.getAlbumId());

            Track track = Track.builder()
                    .name(uploadTrackRequest.getTrackName())
                    .artist(artist)
                    .album(album)
                    .format(musicFormat)
                    .size(uploadTrackRequest.getFile().getSize())
                    .key(fileHash)
                    .uploaderId(principal.getId())
                    .uploadDate(LocalDate.now())
                    .build();

            trackRepository.save(track);

            s3StorageService.uploadTrackInStorage(uploadTrackRequest, fileHash, yandexStorageConfigData.getMusicBucketName());
            return UploadTrackResponse.builder()
                    .id(track.getId())
                    .key(fileHash)
                    .build();
        } catch (DataIntegrityViolationException ex) {
            throw new ServiceException(ALREADY_EXIST, "key");
        } catch (IOException ex) {
            throw new ServiceException(INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Override
    public DownloadTrackResponse getTrackDownloadLink(String trackHash) {
        checkNotBlank(trackHash, "track");
        return DownloadTrackResponse.builder()
                .downloadLink(s3StorageService.getFileLink(trackHash, yandexStorageConfigData.getMusicBucketName()))
                .build();
    }

    private List<SelectTrackResponse> trackStreamToSelectTrackResponseListMap(Stream<Track> trackStream) {
        return trackStream
                .map(track -> {
                    SelectTrackResponse selectTrackResponse = trackMapper.toSearchTrackResponse(track);
                    selectTrackResponse.setArtistId(track.getArtist().getId());
                    selectTrackResponse.setAlbumId(track.getAlbum().getId());
                    return selectTrackResponse;
                })
                .toList();
    }
}
