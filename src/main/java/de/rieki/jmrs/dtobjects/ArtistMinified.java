package de.rieki.jmrs.dtobjects;

import de.rieki.jmrs.domain.Artist;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ArtistMinified {

    private Long artistId;
    private String artistName;
    private List<AlbumMinified> albums;


    public ArtistMinified(Artist artist) {
        this.artistId = artist.getId();
        this.artistName = artist.getName();
        this.albums = artist.getAlbums().stream().map(a -> new AlbumMinified(a)).collect(Collectors.toList());
    }
}
