package de.rieki.jmrs.dtobjects;

import de.rieki.jmrs.domain.Artist;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
public class ArtistListMinified {

    private String artist;
    private Long id;
    private Integer songCount;
    private Integer albumCount;
    private String cover_url;

    public ArtistListMinified(Artist artist) {
        this.artist = artist.getName();
        this.id = artist.getId();
        this.songCount = artist.getAlbums().stream().map(a -> a.getSongs().size()).reduce(0, (a,b) -> a + b);
        this.albumCount = artist.getAlbums().size();
        this.cover_url = new CoverURL("/songs/cover",artist.getAlbums().get(0).getSongs().iterator().next().getCover()).getCover_url();
    }
}
