package de.rieki.jmrs.services;

import de.rieki.jmrs.domain.Album;
import org.springframework.data.domain.Page;

import java.util.Set;

public interface AlbumService {
    Page<Album> getAlbums(int page);
    Set<Album> getAlbums();
    Album findById(Long id);
}
