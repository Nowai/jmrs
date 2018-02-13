package de.rieki.jmrs.scanner;

import de.rieki.jmrs.commands.FileCommand;
import de.rieki.jmrs.commands.SongCommand;
import de.rieki.jmrs.services.AlbumService;
import de.rieki.jmrs.services.ArtistService;
import lombok.NoArgsConstructor;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagField;
import org.jaudiotagger.tag.images.Artwork;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@NoArgsConstructor
public class FileMetaDataScanner {

    // String will be used as a default tag when certain (id3)tags are not set in the music file
    private static final String DEFAULT_TAG = "[Unknown]";
    private static final String DEFAULT_YEAR = "1900";
    private static final String DEFAULT_TRACK_NUMBER = "1";


    public Set<SongCommand> scanMetaData(Set<FileCommand> files) {
        return files.stream()
                .map(this::parseFile)
                .collect(Collectors.toSet());
    }

    private SongCommand parseFile(FileCommand fileCommand) {
        SongCommand song = new SongCommand();

        // Create AudioFile
        try {
            File file = fileCommand.getPath().toFile();
            AudioFile audioFile = AudioFileIO.read(file);
            // Read Tag and Properties
            Tag tag = audioFile.getTag();
            AudioHeader audioHeader = audioFile.getAudioHeader();

            //
            // parse meta-data
            //
            song.setPath(fileCommand.getPath().toString());

            // song title from id3-tag, defaults to file name
            String songTitle = tag.getFirst(FieldKey.TITLE).trim();
            if(songTitle == null || songTitle.trim().isEmpty())
                songTitle = file.getName();
            song.setTitle(songTitle);
            // following tags will be read from id3-tag, dafaults to DEFAULT_TAG
            String songArtist = tag.getFirst(FieldKey.ARTIST).trim();
            if(songArtist == null || songArtist.trim().isEmpty())
                songArtist = DEFAULT_TAG;
            song.setArtist(songArtist);

            String songAlbum = tag.getFirst(FieldKey.ALBUM).trim();
            if(songAlbum == null || songAlbum.trim().isEmpty())
                songAlbum = DEFAULT_TAG;
            song.setAlbum(songAlbum);

            String songAlbumArtist = tag.getFirst(FieldKey.ALBUM_ARTIST).trim();
            if(songAlbumArtist == null || songAlbumArtist.trim().isEmpty())
                songAlbumArtist = DEFAULT_TAG;
            song.setAlbum_artist(songAlbumArtist);

            String songYear = tag.getFirst(FieldKey.YEAR).trim();
            if(songYear == null || songYear.trim().isEmpty())
                songYear = DEFAULT_YEAR;
            song.setYear(songYear);

            String songGenre = tag.getFirst(FieldKey.GENRE).trim();
            if(songGenre == null || songGenre.trim().isEmpty())
                songGenre = "none";
            song.setGenre(songGenre);

            String songTrackNumber = tag.getFirst(FieldKey.TRACK).trim();
            if(songTrackNumber == null || songTrackNumber.trim().isEmpty())
                songTrackNumber = DEFAULT_TRACK_NUMBER;
            song.setTrack_number(songTrackNumber);

            String songCDNumber = tag.getFirst(FieldKey.DISC_NO).trim();
            if(songCDNumber == null || songCDNumber.trim().isEmpty())
                songCDNumber = DEFAULT_TRACK_NUMBER;
            song.setCd_number(songCDNumber);

            String songRating = tag.getFirst(FieldKey.RATING).trim();
            if(songRating == null || songRating.trim().isEmpty())
                songRating = "0";
            song.setRating(new Integer(songRating));

            // parse cover
            Artwork artwork = audioFile.getTag().getFirstArtwork();
            if(artwork != null) {
                song.setCover(artwork.getBinaryData());
                song.setCover_mime_type(artwork.getMimeType());
            } else {
                song.setCover(null);
                song.setCover_mime_type(null);
            }
            // parse properties
            song.setDuration(Optional.ofNullable(audioHeader.getTrackLength()).orElse(100));
            song.setBit_rate(audioHeader.getBitRate());
            song.setType(audioHeader.getFormat());
        } catch(Exception e) {
           e.printStackTrace();
        }
        return song;
    }


}
