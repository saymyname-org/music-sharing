package ru.improve.openfy.core.dao.imp;

import ru.improve.openfy.core.models.Track;

import java.util.List;

public interface SearchingDao {

    List<Track> findMaterials(String queryString);
}
