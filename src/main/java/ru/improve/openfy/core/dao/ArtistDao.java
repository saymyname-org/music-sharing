package ru.improve.openfy.core.dao;

import ru.improve.openfy.core.models.Artist;

import java.util.List;

public interface ArtistDao {

    List<Artist> findAllArtistsByName(String name);
}
