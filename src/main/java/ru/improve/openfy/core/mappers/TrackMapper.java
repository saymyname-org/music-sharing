package ru.improve.openfy.core.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.improve.openfy.api.dto.track.SelectTrackResponse;
import ru.improve.openfy.core.models.Track;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface TrackMapper {

    SelectTrackResponse toSearchTrackResponse(Track track);
}
