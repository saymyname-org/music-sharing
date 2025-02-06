package ru.improve.openfy.core.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.improve.openfy.api.dto.albums.SelectAlbumResponse;
import ru.improve.openfy.core.models.Album;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface AlbumMapper {

    SelectAlbumResponse toSelectAlbumsResponse(Album album);

//    Album toAlbum();
}
