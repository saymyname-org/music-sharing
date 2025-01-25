package ru.improve.openfy.core.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.improve.openfy.api.dto.searching.SearchTrackResponse;
import ru.improve.openfy.core.models.Track;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface SearchTrackMapper {

    SearchTrackResponse mapToSearchTrackResponse(Track track);
}
