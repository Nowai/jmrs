package de.rieki.jmrs.converters;

import de.rieki.jmrs.commands.SongCommand;
import de.rieki.jmrs.domain.Artist;
import de.rieki.jmrs.domain.Song;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class SongCommandToSong implements Converter<SongCommand, Song> {
    @Synchronized
    @Nullable
    @Override
    public Song convert(SongCommand songCommand) {
        if(songCommand == null)
            return null;

        final Song song = new Song();
        song.setPath(songCommand.getPath());
        song.setTitle(songCommand.getTitle());
        song.setYear(songCommand.getYear());
        song.setGenre(songCommand.getGenre());
        song.setCover(songCommand);
        if(song.getCover() != null)
            song.getCover().setSong(song);
        song.setTrack_number(songCommand.getTrack_number());
        song.setCd_number(songCommand.getCd_number());
        song.setRating(songCommand.getRating());
        song.setDuration(songCommand.getDuration());
        song.setDate_added(songCommand.getDate_added());
        song.setPlay_count(0);
        song.setSkipped_cound(0);
        song.setBit_rate(songCommand.getBit_rate());
        song.setType(songCommand.getType());

        return song;
    }
}
