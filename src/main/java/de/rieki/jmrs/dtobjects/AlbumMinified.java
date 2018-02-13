package de.rieki.jmrs.dtobjects;

import de.rieki.jmrs.domain.Album;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class AlbumMinified {

    private Long albumId;
    private String albumTitle;
    private Long artistId;
    private String artistName;
    private List<SongMinified> songs;

    public AlbumMinified(Album album) {
        this.albumId = album.getId();
        this.albumTitle = album.getTitle();
        this.artistId = album.getArtist().getId();
        this.artistName = album.getArtist().getName();
        this.songs = album.getSongs().stream().map(s -> new SongMinified(s)).collect(Collectors.toList());
    }
}
