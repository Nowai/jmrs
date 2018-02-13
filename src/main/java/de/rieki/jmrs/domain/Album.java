package de.rieki.jmrs.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.rieki.jmrs.commands.SongCommand;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@EqualsAndHashCode(exclude = {"songs"})
@Data
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Artist artist;

    private String title;
    @Lob
    private Byte[] cover;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "album")
    private Set<Song> songs = new HashSet<>();

    @JsonIgnore
    public Byte[] getCover() {
        return cover;
    }

    @JsonIgnore
    public Artist getArtist() {
        return artist;
    }

    public Album addSong(Song song, SongCommand songCommand) {
        song.setAlbum(this);
        songs.add(song);
       return this;
    }

}
