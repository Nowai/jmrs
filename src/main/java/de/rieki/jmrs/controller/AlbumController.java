package de.rieki.jmrs.controller;

import de.rieki.jmrs.domain.Album;
import de.rieki.jmrs.dtobjects.AlbumListMinified;
import de.rieki.jmrs.dtobjects.AlbumMinified;
import de.rieki.jmrs.dtobjects.AlbumListPage;
import de.rieki.jmrs.exceptions.ResourceNotFoundException;
import de.rieki.jmrs.services.AlbumService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class AlbumController {

    private final AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    /**
     * Returns the first page of Album entries
     * @return
     */
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping
    @RequestMapping("/albums")
    public AlbumListPage getAllAlbums() {
        return getAlbums(0);
    }


    /**
     * Returns an album by the given id
     * @param id
     * @return
     */
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping
    @RequestMapping("/albums/{id}")
    public AlbumMinified getAlbumById(@PathVariable(name = "id")int id) {
        Long idL;
        Album album;
        AlbumMinified albumMinified;
        try {
            idL = new Long(id);
            album = albumService.findById(idL);
            albumMinified = new AlbumMinified(album);
        } catch(RuntimeException e) {
            throw new ResourceNotFoundException();
        }
        return albumMinified;
    }

    /**
     * Returns an album page corresponding to the given page number
     * @param page
     * @return
     */
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping
    @RequestMapping(value = "/albums", params = {"page"})
    public AlbumListPage getAlbums(@RequestParam("page")int page) {
        return new AlbumListPage(albumService.getAlbums(page), "/albums");
    }
}
