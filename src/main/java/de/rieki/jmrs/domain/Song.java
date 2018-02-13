package de.rieki.jmrs.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.rieki.jmrs.commands.SongCommand;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(exclude = {"album", "artist", "cover"})
@Entity
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String path;
    private String title;
    @ManyToOne
    private Artist artist;
    @ManyToOne
    private Album album;
    private String year;
    private String genre;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "song")
    private Artwork cover;
    private String track_number;
    private String cd_number;
    private Integer rating;
    // song properties
    private Integer duration;
    private String date_added;
    private Integer play_count;
    private Integer skipped_cound;
    private String bit_rate;
    private String type;

    public void setCover(SongCommand songCommand) {
        if(songCommand.getCover() != null && songCommand.getCover_mime_type() != null)
            this.cover = new Artwork(songCommand.getCover(), songCommand.getCover_mime_type());
        else
            this.cover = null;
    }

    @JsonIgnore
    public Artwork getCover() {
        return cover;
    }

    @JsonIgnore
    public Album getAlbum() {
        return album;
    }

    @JsonIgnore
    public Artist getArtist() {
        return artist;
    }

}
