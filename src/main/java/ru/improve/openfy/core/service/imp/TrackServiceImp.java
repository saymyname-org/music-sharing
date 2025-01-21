package ru.improve.openfy.core.service.imp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.improve.openfy.api.dto.upload.UploadTrackRequest;
import ru.improve.openfy.api.error.ErrorCode;
import ru.improve.openfy.api.error.ServiceException;
import ru.improve.openfy.core.models.Track;
import ru.improve.openfy.core.security.UserPrincipal;
import ru.improve.openfy.core.service.S3ClientService;
import ru.improve.openfy.core.service.TrackService;
import ru.improve.openfy.repositories.TrackRepository;
import ru.improve.openfy.util.FileHashCalculator;

import java.io.IOException;

import static ru.improve.openfy.api.error.ErrorCode.ALREADY_EXIST;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrackServiceImp implements TrackService {

    private final TrackRepository trackRepository;

    private final S3ClientService s3ClientService;

    @Transactional
    @Override
    public void saveTrack(UploadTrackRequest uploadTrackRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();

        try {
            String hashFile = FileHashCalculator.getHashFromFileInputString(uploadTrackRequest.getFile().getInputStream());

            Track track = Track.builder()
                    .name(uploadTrackRequest.getTrackName())
                    .authorName(uploadTrackRequest.getAuthorName())
                    .size((int) uploadTrackRequest.getFile().getSize())
                    .hash(hashFile)
                    .uploaderId(principal.getId())
                    .build();

            trackRepository.save(track);

            s3ClientService.uploadTrackInStorage(uploadTrackRequest, hashFile);
        } catch (DataIntegrityViolationException ex) {
            throw new ServiceException(ALREADY_EXIST, new String[]{"hash"});
        } catch (IOException ex) {
            throw new ServiceException(ErrorCode.INTERNAL_SERVER_ERROR, ex);
        }
    }
}
