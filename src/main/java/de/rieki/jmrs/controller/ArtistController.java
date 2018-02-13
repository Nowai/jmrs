package de.rieki.jmrs.controller;

import de.rieki.jmrs.domain.Artist;
import de.rieki.jmrs.dtobjects.ArtistListMinified;
import de.rieki.jmrs.dtobjects.ArtistMinified;
import de.rieki.jmrs.dtobjects.ArtistListPage;
import de.rieki.jmrs.exceptions.ResourceNotFoundException;
import de.rieki.jmrs.services.ArtistService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ArtistController {

    private final ArtistService artistService;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    /**
     * Returns the first artistlist page
     * @return
     */
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping
    @RequestMapping("/artists")
    public ArtistListPage getAllArtists() {
        return getArtists(0);
    }

    /**
     * Returns an artist with the given id
     * @param id
     * @return
     */
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping
    @RequestMapping("/artists/{id}")
    public ArtistMinified getArtist(@PathVariable(name = "id")int id) {
        Long idL;
        Artist artist;
        ArtistMinified artistMinified;
        try {
            idL = new Long(id);
            artist = artistService.findById(idL);
            artistMinified = new ArtistMinified(artist);
        } catch(RuntimeException e) {
            throw new ResourceNotFoundException();
        }
        return artistMinified;
    }

    /**
     * Returns the corresponding artist page with the given page number
     * @param page
     * @return
     */
    @CrossOrigin(origins =  "http://localhost:3000")
    @GetMapping
    @RequestMapping(value = "/artists", params = {"page"})
    public ArtistListPage getArtists(@RequestParam("page")int page) {
       return new ArtistListPage(artistService.getArtists(page), "/artists");
    }
}
