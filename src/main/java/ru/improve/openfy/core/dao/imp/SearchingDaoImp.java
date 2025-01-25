package ru.improve.openfy.core.dao.imp;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.improve.openfy.core.dao.SearchingDao;
import ru.improve.openfy.core.models.Track;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SearchingDaoImp implements SearchingDao {

    private final EntityManager em;

    @Transactional
    @Override
    public List<Track> findMaterials(String request) {
        Query query = em.createNativeQuery(
                    """
                    select id, name, author_name, format, size, hash, uploader from tracks t
                    where t.name ~* (:query || '*') or 
                    t.author_name ~* (:query || '*')
                    """,
                        Track.class)
                .setParameter("query", request);

        return query.getResultList();
    }
}
