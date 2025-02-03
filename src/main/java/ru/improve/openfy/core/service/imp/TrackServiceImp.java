package ru.improve.openfy.core.service.imp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.improve.openfy.api.dto.searching.DownloadTrackResponse;
import ru.improve.openfy.api.dto.upload.UploadTrackRequest;
import ru.improve.openfy.api.error.ServiceException;
import ru.improve.openfy.core.configuration.YandexStorageConfigData;
import ru.improve.openfy.core.models.Track;
import ru.improve.openfy.core.security.UserPrincipal;
import ru.improve.openfy.core.service.S3StorageService;
import ru.improve.openfy.core.service.TrackService;
import ru.improve.openfy.core.track.enums.MusicFormat;
import ru.improve.openfy.repositories.TrackRepository;
import ru.improve.openfy.util.EnumMapper;
import ru.improve.openfy.util.FileHashCalculator;

import java.io.IOException;

import static ru.improve.openfy.api.error.ErrorCode.ALREADY_EXIST;
import static ru.improve.openfy.api.error.ErrorCode.INTERNAL_SERVER_ERROR;
import static ru.improve.openfy.core.validators.Validators.checkNotBlank;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrackServiceImp implements TrackService {

    private final TrackRepository trackRepository;

    private final S3StorageService s3StorageService;

    private final YandexStorageConfigData yandexStorageConfigData;

    @Transactional
    @Override
    public void uploadTrack(UploadTrackRequest uploadTrackRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();

        try {
            String hashFile = FileHashCalculator.getHashFromFileInputString(uploadTrackRequest.getFile().getInputStream());

            MusicFormat mf = EnumMapper.enumFromString(uploadTrackRequest.getMusicFormat(), MusicFormat.class, "music format");

            Track track = Track.builder()
                    .name(uploadTrackRequest.getTrackName())
//                    .authorName(uploadTrackRequest.getAuthorName())
                    .size(uploadTrackRequest.getFile().getSize())
                    .format(mf)
                    .hash(hashFile)
                    .uploaderId(principal.getId())
                    .build();

            trackRepository.save(track);

            s3StorageService.uploadTrackInStorage(uploadTrackRequest, hashFile, yandexStorageConfigData.getMusicBucketName());
        } catch (DataIntegrityViolationException ex) {
            throw new ServiceException(ALREADY_EXIST, "hash");
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
}
