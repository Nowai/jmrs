package de.rieki.jmrs.dtobjects;

import de.rieki.jmrs.domain.Artist;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ArtistListPage {
    private String next_href;
    private List<ArtistListMinified> artists;

    public ArtistListPage(Page<Artist> page, String baseUrl) {
        if(page.hasNext())
            this.next_href = baseUrl + "?page=" + (1+page.getNumber());
        else
            this.next_href = "";
        this.artists = new ArrayList<>();
        page.getContent().stream().forEachOrdered(a -> artists.add(new ArtistListMinified(a)));
    }
}

