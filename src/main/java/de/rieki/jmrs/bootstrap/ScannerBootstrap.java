package de.rieki.jmrs.bootstrap;

import de.rieki.jmrs.commands.SongCommand;
import de.rieki.jmrs.domain.Song;
import de.rieki.jmrs.repositories.SongRepository;
import de.rieki.jmrs.scanner.MusicScanner;
import de.rieki.jmrs.services.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Component
public class ScannerBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private final MusicScanner musicScanner;

    @Autowired
    private final SongService songService;

    // Add your folder to scan to the list
    private final List<String> paths = Arrays.asList("");

    public ScannerBootstrap(MusicScanner musicScanner, SongService songService) {
        this.musicScanner = musicScanner;
        this.songService = songService;
    }

    /**
     * Scan for files at the given paths at the start of the application
     * @param contextRefreshedEvent
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        Set<SongCommand> scannedSongs = musicScanner.scanMusic(paths);
        scannedSongs.stream().forEach(songService::saveSong);
    }
}
