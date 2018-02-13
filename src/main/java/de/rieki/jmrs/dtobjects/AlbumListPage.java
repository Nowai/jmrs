package de.rieki.jmrs.dtobjects;

import de.rieki.jmrs.domain.Album;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Getter
public class AlbumListPage {
    private String next_href;
    private List<AlbumListMinified> albums;

    public AlbumListPage(Page<Album> page, String baseUrl) {
        if(page.hasNext())
            this.next_href = baseUrl + "?page=" + (1+page.getNumber());
        else
            this.next_href = "";
        this.albums = new ArrayList<>();
        page.getContent().stream().forEachOrdered(a -> albums.add(new AlbumListMinified(a)));
    }
}
