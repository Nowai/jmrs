package de.rieki.jmrs.services;

import de.rieki.jmrs.domain.Artist;
import org.springframework.data.domain.Page;

import java.util.Set;

public interface ArtistService {
    Set<Artist> getArtists();
    Artist findById(Long l);
    Artist findByName(String name);
    Page<Artist> getArtists(int page);
}
