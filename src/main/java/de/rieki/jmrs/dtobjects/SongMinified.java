package de.rieki.jmrs.dtobjects;


import de.rieki.jmrs.domain.Artwork;
import de.rieki.jmrs.domain.Song;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class SongMinified {
    // data
    private Long songId;
    private String title;
    private String artist;
    private Long artistId;
    private String album;
    private Long albumId;
    private Integer duration;
    private Integer rating;
    private String cover_url;

    public SongMinified(Song song) {
        this.songId = song.getId();
        this.title = song.getTitle();
        this.artist = song.getArtist().getName();
        this.artistId = song.getArtist().getId();
        this.album = song.getAlbum().getTitle();
        this.albumId = song.getAlbum().getId();
        this.duration = song.getDuration();
        this.rating = song.getRating();
        Artwork artwork = song.getCover();
        this.cover_url = new CoverURL("/songs/cover",song.getCover()).getCover_url();
    }
}
