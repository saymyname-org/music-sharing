package ru.improve.openfy.core.service.imp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.improve.openfy.api.dto.upload.UploadTrackRequest;
import ru.improve.openfy.api.error.ServiceException;
import ru.improve.openfy.core.configuration.storage.YandexStorageConfigData;
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

@Service
@RequiredArgsConstructor
@Slf4j
public class TrackServiceImp implements TrackService {

    private final TrackRepository trackRepository;

    private final S3StorageService s3StorageService;

    private final YandexStorageConfigData yandexStorageConfigData;

    @Transactional
    @Override
    public void saveTrack(UploadTrackRequest uploadTrackRequest) {
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
            throw new ServiceException(ALREADY_EXIST, new String[]{"hash"});
        } catch (IOException ex) {
            throw new ServiceException(INTERNAL_SERVER_ERROR, ex);
        }
    }
}
