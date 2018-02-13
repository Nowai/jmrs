package de.rieki.jmrs.services;

import de.rieki.jmrs.commands.SongCommand;
import de.rieki.jmrs.converters.SongCommandToSong;
import de.rieki.jmrs.domain.Album;
import de.rieki.jmrs.domain.Artwork;
import de.rieki.jmrs.domain.Song;
import de.rieki.jmrs.domain.Artist;
import de.rieki.jmrs.repositories.AlbumRepository;
import de.rieki.jmrs.repositories.SongRepository;
import de.rieki.jmrs.repositories.ArtistRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SongServiceImpl implements SongService {
    @Autowired
    private final SongRepository songRepository;
    @Autowired
    private final AlbumRepository albumRepository;
    @Autowired
    private final ArtistRepository artistRepository;
    @Autowired
    private final SongCommandToSong songCommandToSong;

    public SongServiceImpl(SongRepository songRepository, AlbumRepository albumRepository, ArtistRepository artistRepository, SongCommandToSong songCommandToSong) {
        this.songRepository = songRepository;
        this.albumRepository = albumRepository;
        this.artistRepository = artistRepository;
        this.songCommandToSong = songCommandToSong;
    }

    @Override
    public File getSongFile(Long id) {
        Optional<Song> songOptional = songRepository.findById(id);
        if(!songOptional.isPresent())
            return null;
        String path = songOptional.get().getPath();
        if(!Files.isRegularFile(Paths.get(path)))
            throw new RuntimeException("Song file not found");
        File songFile = new File(path);
        return songFile;
    }

    @Override
    public byte[] getCover(Long id, Artwork.Size size) {
        Optional<Song> songOptional = songRepository.findById(id);
        byte[] cover = null;
        if(songOptional.isPresent() && songOptional.get().getCover() != null) {
           if(size.equals(Artwork.Size.SIZE_500))
               cover = songOptional.get().getCover().getCover500x500();
           if(size.equals(Artwork.Size.SIZE_100))
               cover = songOptional.get().getCover().getCover100x100();
           if(size.equals(Artwork.Size.SIZE_50))
               cover = songOptional.get().getCover().getCover50x50();
           if(cover != null) {
               return cover;
           }
        }
        try {
            Resource resource = new ClassPathResource("static/placeholder.jpg");
            InputStream resourceInputStream = resource.getInputStream();
            cover = IOUtils.toByteArray(resourceInputStream);
        }catch(IOException ioe) {
            log.error("Can't find placeholder graphic for covers");
            ioe.printStackTrace();
        }
        return cover;
    }

    @Override
    public Set<Song> getSongs() {
        Set<Song> songSet = new HashSet<>();
        songRepository.findAll().iterator().forEachRemaining(songSet::add);
        return songSet;
    }

    @Override
    public Song findById(Long l) {
        Optional<Song> songOptional = songRepository.findById(l);

        if(!songOptional.isPresent())
            throw new RuntimeException("Song not found");

        return songOptional.get();
    }

    @Transactional
    @Override
    public Song saveSong(SongCommand songCommand) {
        log.debug("Saving songCommand...");
        Song detachedSong = songCommandToSong.convert(songCommand);
        log.debug("\tSaving song with path " + detachedSong.getPath());

        String artist = songCommand.getArtist();
        Optional<Artist> artistOptional = artistRepository.findByName(artist);
        Artist artistObj = artistOptional.orElse(new Artist(artist));
        artistObj.addSong(detachedSong, songCommand);
        artistRepository.save(artistObj);

        return detachedSong;
    }

    private Album createNewAlbum(Artist artist, String title) {
        Album albumObj = new Album();
        albumObj.setArtist(artist);
        albumObj.setTitle(title);
        return albumObj;
    }

    @Override
    public Set<Song> findByArtist(String artist) {
        Set<Song> songSet = getSongs();

        Set<Song> artistSet = songSet.stream()
                .filter(song_ -> song_.getArtist().getName().contains(artist))
                .collect(Collectors.toSet());
        return artistSet;
    }

    @Override
    public Page<Song> getSongs(int page) {
        return songRepository.findAll(new PageRequest(page, 50, Sort.Direction.ASC, "title"));
    }
}
