package de.rieki.jmrs.dtobjects;

import de.rieki.jmrs.domain.Artwork;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CoverURL {
    private String cover_url;

    public CoverURL(String baseURL, Artwork artwork) {
        if(artwork != null) {
            Long id = artwork.getSong().getId();
            if(id==null)
                id = 9999999999L;
            this.cover_url = baseURL + "/" + id;
        }
        else // ugly hack to get a default cover :(
            this.cover_url = baseURL + "/9999999";
    }
}
