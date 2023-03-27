package com.imersao.spring.imdb;

import com.imersao.spring.sticker.Content;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/imdb")
public class ImdbController {

    @Autowired
    private ImdbService imdbService;

    @GetMapping("/top-250-movies")
    public List<Content> getTop250Movies() throws Exception {
        return imdbService.getTop250Movies();
    }

    @GetMapping("/top-250-tvs")
    public List<Content> getTop250Tv() throws Exception {
        return imdbService.getTop250Tvs();
    }

    @GetMapping("/movies/{title}")
    public List<Content> findMoviesByTitle(@PathVariable("title") String title) throws Exception {
        return imdbService.findMoviesByTitle(title);
    }

    @GetMapping("/series/{title}")
    public List<Content> findSeriesByTitle(@PathVariable("title") String title) throws Exception {
        return imdbService.findSeriesByTitle(title);
    }
}
