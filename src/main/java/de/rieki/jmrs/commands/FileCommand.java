package de.rieki.jmrs.commands;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;

@Setter
@Getter
@EqualsAndHashCode
public class FileCommand {

    public FileCommand(Path path) {
        this.path = path;
    }

    private Path path;
}
