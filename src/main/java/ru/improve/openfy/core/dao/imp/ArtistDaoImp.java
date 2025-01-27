package ru.improve.openfy.core.dao.imp;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.improve.openfy.core.dao.ArtistDao;
import ru.improve.openfy.core.models.Artist;

import java.util.List;

@RequiredArgsConstructor
@Component
public class ArtistDaoImp implements ArtistDao {

    private final EntityManager em;

    @Override
    public List<Artist> findAllArtists(String name) {
        Query query = em.createNativeQuery(
                        """
                        select * from artists a
                        where a.name ~* (:name || '*')
                        """,
                        Artist.class)
                .setParameter("name", name);
        return query.getResultList();
    }
}
