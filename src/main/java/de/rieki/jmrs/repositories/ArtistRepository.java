package de.rieki.jmrs.repositories;

import de.rieki.jmrs.domain.Artist;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArtistRepository extends PagingAndSortingRepository<Artist, Long> {
    Optional<Artist> findByName(String name);
    Optional<Artist> findById(Long id);
}
