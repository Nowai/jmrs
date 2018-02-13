package de.rieki.jmrs.scanner;

import de.rieki.jmrs.commands.FileCommand;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@NoArgsConstructor
public class DirectoryScanner {
    private final List<String> supportedFileTypes = Arrays.asList("mp3", "flac", "ogg", "mp4", "aiff", "wav", "wma", "dsf");

    public Set<FileCommand> scanFiles(List<String> directories) {
        Set<FileCommand> fileSet;

        fileSet = directories.stream()
                .map(this::sanitizePath)                        // sanitize all directory filepaths
                .map(this::getPaths)                            // walk the directory
                .reduce(Stream::concat)                         // combine all path streams
                .orElseGet(Stream::empty)                       // or empty stream
                .filter(Files::isRegularFile)                   // filter out all non files
                .filter(this::filterFiletype)                   // filter out all non-supported files
                .map(FileCommand::new)                          // convert Path -> FileCommand
                .collect(Collectors.toSet());                   // get a set of all supported files

        return fileSet;
    }

    private boolean filterFiletype(Path path) {
        return supportedFileTypes.stream()
                .anyMatch(fileType_ -> path.toString().toLowerCase().endsWith(fileType_));
    }

    private String sanitizePath(String path) {
        if(path.startsWith("~"))
            return System.getProperty("user.home") + path.substring(1);
        return path;
    }

    private Stream<Path> getPaths(String path) {
        Stream<Path> paths;
        try {
            paths = Files.walk(Paths.get(path));
        } catch(IOException ioe) {
            paths = Stream.empty();
            ioe.printStackTrace();
        }
        return paths;
    }
}
