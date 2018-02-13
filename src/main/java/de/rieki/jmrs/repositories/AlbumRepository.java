package de.rieki.jmrs.repositories;

import de.rieki.jmrs.domain.Album;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlbumRepository extends PagingAndSortingRepository<Album, Long> {
    Optional<Album> findByTitle(String album);
    Optional<Album> findByTitleAndArtist(String album, String album_arist);
}
