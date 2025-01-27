package ru.improve.openfy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.improve.openfy.core.models.Track;

@Repository
public interface TrackRepository extends JpaRepository<Track, Long> {

}
