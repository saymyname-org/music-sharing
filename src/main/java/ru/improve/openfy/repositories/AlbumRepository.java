package ru.improve.openfy.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.improve.openfy.core.models.Album;

import java.util.List;

@Repository
public interface AlbumRepository extends
        JpaRepository<Album, Integer>,
        PagingAndSortingRepository<Album, Integer> {

    Page<Album> findAll(Pageable page);

    List<Album> findAllByName(String name, Pageable page);
}
