package de.rieki.jmrs.dtobjects;

import de.rieki.jmrs.domain.Song;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Getter
public class SongPage {
    private String next_href;
    private List<SongMinified> songs;

    public SongPage(Page<Song> page, String baseUrl) {
        if(page.hasNext())
            this.next_href = baseUrl + "?page=" + (1+page.getNumber());
        else
            this.next_href = "";
        this.songs = new ArrayList<>();
        page.getContent().stream().forEachOrdered(s -> songs.add(new SongMinified(s)));
    }
}
