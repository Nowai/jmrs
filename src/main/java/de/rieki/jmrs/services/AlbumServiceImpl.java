package de.rieki.jmrs.services;

import de.rieki.jmrs.domain.Album;
import de.rieki.jmrs.repositories.AlbumRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AlbumServiceImpl implements AlbumService{

    private final AlbumRepository albumRepository;

    public AlbumServiceImpl(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    @Override
    public Page<Album> getAlbums(int page) {
        return albumRepository.findAll(new PageRequest(page, 50, Sort.Direction.ASC,"title"));
    }

    @Override
    public Set<Album> getAlbums() {
        Set<Album> set = new HashSet<>();
        albumRepository.findAll().iterator().forEachRemaining(set::add);
        return set;
    }

    @Override
    public Album findById(Long id) {
        Optional<Album> albumOptional = albumRepository.findById(id);
        if(albumOptional.isPresent())
            return albumOptional.get();
        throw new RuntimeException();
    }
}
