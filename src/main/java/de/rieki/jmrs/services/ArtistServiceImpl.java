package de.rieki.jmrs.services;

import de.rieki.jmrs.domain.Artist;
import de.rieki.jmrs.repositories.ArtistRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ArtistServiceImpl implements ArtistService {

    private final ArtistRepository artistRepository;

    public ArtistServiceImpl(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    @Override
    public Set<Artist> getArtists() {
        Set<Artist> set = new HashSet<>();
        artistRepository.findAll().iterator().forEachRemaining(set::add);
        return set;
    }

    @Override
    public Artist findById(Long l) {
        Optional<Artist> artistOptional = artistRepository.findById(l);
        if(artistOptional.isPresent())
            return artistOptional.get();
        throw new RuntimeException();
    }

    @Override
    public Artist findByName(String name) {
        Optional<Artist> artistOptional = artistRepository.findByName(name);
        if(artistOptional.isPresent())
            return artistOptional.get();
        throw new RuntimeException();
    }

    @Override
    public Page<Artist> getArtists(int page) {
        return artistRepository.findAll(new PageRequest(page, 50, Sort.Direction.ASC, "name"));
    }
}
