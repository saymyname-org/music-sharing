package ru.improve.openfy.core.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.improve.openfy.api.dto.artist.CreateArtistRequest;
import ru.improve.openfy.api.dto.artist.SelectArtistResponse;
import ru.improve.openfy.core.models.Artist;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ArtistMapper {

    Artist toArtist(CreateArtistRequest createArtistRequest);

    SelectArtistResponse toSearchArtistResponse(Artist artist);
}
