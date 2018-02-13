package de.rieki.jmrs.commands;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString(exclude = "cover")
public class SongCommand {
    // song meta-data
    private String path;
    private String title;
    private String artist;
    private String album;
    private String album_artist;
    private String year;
    private String genre;
    private byte[] cover;
    private String cover_mime_type;
    private String track_number;
    private String cd_number;
    private Integer rating;
    // song properties
    private Integer duration;
    private String date_added;
    private Integer play_count;
    private Integer skipped_cound;
    private String bit_rate;
    private String type;

}
