package de.rieki.jmrs.domain;

import de.rieki.jmrs.commands.SongCommand;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@Entity
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // Relationship to Albums
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "artist")
    private List<Album> albums = new ArrayList<Album>();

    public Artist(String name) {
        this.name = name;
    }

    public Artist() {}

    public Artist addSong(Song song, SongCommand songCommand) {
        song.setArtist(this);
        Optional<Album> albumOptional = albums.stream()
                .filter(a -> a.getTitle().equals(songCommand.getAlbum()))
                .findFirst();
        if(albumOptional.isPresent()) {
            albumOptional.get().addSong(song, songCommand);
        } else {
            Album albumObj = new Album();
            albumObj.setArtist(this);
            albumObj.setTitle(songCommand.getAlbum());
            albumObj.addSong(song, songCommand);
            albums.add(albumObj);
        }
        return this;
    }


}
