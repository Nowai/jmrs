package de.rieki.jmrs.services;

import de.rieki.jmrs.domain.Artwork;
import de.rieki.jmrs.repositories.ArtworkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArtworkServiceImpl implements ArtworkService{

    @Autowired
    ArtworkRepository artworkRepository;

    public ArtworkServiceImpl(ArtworkRepository artworkRepository) {
        this.artworkRepository = artworkRepository;
    }

    @Override
    public List<Artwork> getArtworks() {
        List<Artwork> artworkList = new ArrayList<>();
        artworkRepository.findAll().iterator().forEachRemaining(artworkList::add);
        return artworkList;
    }
}
