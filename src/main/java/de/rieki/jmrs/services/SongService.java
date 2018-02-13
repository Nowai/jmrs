package de.rieki.jmrs.services;

import de.rieki.jmrs.commands.SongCommand;
import de.rieki.jmrs.domain.Artwork;
import de.rieki.jmrs.domain.Song;
import org.springframework.data.domain.Page;

import java.io.File;
import java.util.Set;

public interface SongService {

    Set<Song> getSongs();
    Page<Song> getSongs(int page);
    Song findById(Long l);
    Set<Song> findByArtist(String artist);
    Song saveSong(SongCommand songCommand);
    File getSongFile(Long id);
    byte[] getCover(Long id, Artwork.Size size);
}
