package de.rieki.jmrs.scanner;

import de.rieki.jmrs.commands.FileCommand;
import de.rieki.jmrs.commands.SongCommand;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@NoArgsConstructor
@Slf4j
@Component
public class MusicScanner {

    @Autowired
    private DirectoryScanner directoryScanner;
    @Autowired
    private FileMetaDataScanner fileMetaDataScanner;

    public MusicScanner(DirectoryScanner directoryScanner, FileMetaDataScanner fileMetaDataScanner) {
        this.directoryScanner = directoryScanner;
        this.fileMetaDataScanner = fileMetaDataScanner;
    }

    public Set<SongCommand> scanMusic(List<String> directories) {
        Set<FileCommand> fileSet = directoryScanner.scanFiles(directories);
        log.debug("Found " + fileSet.size() + " files");
        Set<SongCommand> songSet = fileMetaDataScanner.scanMetaData(fileSet);
        songSet = songSet.stream().filter(s -> s.getTitle() != null).collect(Collectors.toSet());

        return songSet;
    }

}
