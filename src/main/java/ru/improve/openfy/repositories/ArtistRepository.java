package ru.improve.openfy.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.improve.openfy.core.models.Artist;

import java.util.List;

@Repository
public interface ArtistRepository extends
        JpaRepository<Artist, Integer>,
        PagingAndSortingRepository<Artist, Integer> {

    Page<Artist> findAll(Pageable page);

    List<Artist> findAllByName(String request, Pageable page);
}
