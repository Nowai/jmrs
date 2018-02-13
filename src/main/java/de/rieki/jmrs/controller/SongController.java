package de.rieki.jmrs.controller;

import de.rieki.jmrs.domain.Artwork;
import de.rieki.jmrs.domain.Song;
import de.rieki.jmrs.dtobjects.SongMinified;
import de.rieki.jmrs.dtobjects.SongPage;
import de.rieki.jmrs.exceptions.ResourceNotFoundException;
import de.rieki.jmrs.services.SongService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Slf4j
@RestController
public class SongController {

    private final SongService songService;

    public SongController(SongService songService) {
        this.songService = songService;
    }

    /**
     * Returns the first song page
     * @return
     */
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping
    @RequestMapping("/songs")
    public SongPage getSongs() {
        return getSongs(0);
    }

    /**
     * Returns the song page correpsonding to the given page number
     * @param page
     * @return
     */
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping
    @RequestMapping(value = "/songs", params = {"page"})
    public SongPage getSongs(@RequestParam("page")int page) {
       return new SongPage(songService.getSongs(page), "/songs");
    }

    /**
     * Returns the song with the given id
     * @param id
     * @return
     */
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping
    @RequestMapping("/songs/{id}")
    public Song getSongByID(@PathVariable("id") String id) {
        Long idL;
        Song song;
        try {
            idL = new Long(id);
            song = songService.findById(idL);
        } catch(RuntimeException e) {
            throw new ResourceNotFoundException();
        }
        return song;
    }

    /**
     * Returns the song file with the given id
     * @param id
     * @param response
     * @return
     */
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping
    @RequestMapping("/songs/file/{id}")
    @ResponseBody
    public FileSystemResource getSongFile(@PathVariable("id") String id, HttpServletResponse response) {
        response.setContentType("audio/mpeg");
        response.setHeader("Content-Disposition", "attachment; filename=\"song-file.mp3\"");
        return new FileSystemResource(songService.getSongFile(new Long(id)));
    }

    /**
     * Returns the cover of the song with the given id
     * @param id
     * @return
     */
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping
    @RequestMapping(value = "/songs/cover/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getSongCover(@PathVariable("id")Long id) {
        return songService.getCover(id, Artwork.Size.SIZE_500);
    }

    /**
     * Returns the cover in a specific size with the given id
     * @param id
     * @param size
     * @return
     */
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping
    @RequestMapping(value = "/songs/cover/{id}/{size}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getSongSizedCover(@PathVariable("id")Long id, @PathVariable("size")String size) {
        if(size.equals("500"))
            return songService.getCover(id, Artwork.Size.SIZE_500);
        if(size.equals("100"))
            return songService.getCover(id, Artwork.Size.SIZE_100);
        if(size.equals("50"))
            return songService.getCover(id, Artwork.Size.SIZE_50);
        else {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("placeholder.jpg").getFile());
            Path paths = file.toPath();
            byte[] placeholder = null;
            try {
                placeholder = Files.readAllBytes(paths);
            }catch(IOException ioe) {
                ioe.printStackTrace();
                return null;
            }
            return placeholder;
        }
    }
}
