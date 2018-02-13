package de.rieki.jmrs.commands;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class SongCommandMinified {

    // data
    private Long songId;
    private String title;
    private String artist;
    private Long artistId;
    private String album;
    private Long albumId;
    private Integer duration;
    private Integer rating;

}
