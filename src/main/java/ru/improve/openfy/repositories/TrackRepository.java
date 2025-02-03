package ru.improve.openfy.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.improve.openfy.core.models.Track;

import java.util.List;

@Repository
public interface TrackRepository extends
        JpaRepository<Track, Long>,
        PagingAndSortingRepository<Track, Long> {

    Page<Track> findAll(Pageable page);

    List<Track> findAllByNameContainingIgnoreCase(String name, Pageable page);
}
