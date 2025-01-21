package ru.improve.openfy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.improve.openfy.core.models.Track;

public interface TrackRepository extends JpaRepository<Track, Long> {

}
