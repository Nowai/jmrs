package de.rieki.jmrs.dtobjects;

import de.rieki.jmrs.domain.Album;
import lombok.Getter;

@Getter
public class AlbumListMinified {
    private String albumTitle;
    private Long albumId;
    private String artistName;
    private Long artistId;
    private Integer songCount;
    private String cover_url;

    public AlbumListMinified(Album album) {
        this.albumTitle = album.getTitle();
        this.albumId = album.getId();
        this.artistId = album.getArtist().getId();
        this.artistName = album.getArtist().getName();
        this.songCount = album.getSongs().size();
        this.cover_url = new CoverURL("/songs/cover",album.getSongs().iterator().next().getCover()).getCover_url();
    }
}
