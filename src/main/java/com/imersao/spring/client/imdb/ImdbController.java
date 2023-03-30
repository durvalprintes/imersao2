package com.imersao.spring.client.imdb;

import com.imersao.spring.sticker.Content;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/imdb")
@RequiredArgsConstructor
public class ImdbController {

    private final ImdbService imdb;

    @GetMapping("/top-250-movies")
    public List<Content> getTop250Movies(
            @RequestParam(name = "stickers", defaultValue = "5", required = false) Integer stickers) throws Exception {
        return imdb.getTop250Movies(stickers);
    }

    @GetMapping("/top-250-tvs")
    public List<Content> getTop250Tv(
            @RequestParam(name = "stickers", defaultValue = "5", required = false) Integer stickers) throws Exception {
        return imdb.getTop250Tvs(stickers);
    }

    @GetMapping("/movies/{title}")
    public List<Content> findMoviesByTitle(
            @PathVariable("title") String title,
            @RequestParam(name = "stickers", defaultValue = "5", required = false) Integer stickers) throws Exception {
        return imdb.findMoviesByTitle(title, stickers);
    }

    @GetMapping("/series/{title}")
    public List<Content> findSeriesByTitle(
            @PathVariable("title") String title,
            @RequestParam(name = "stickers", defaultValue = "5", required = false) Integer stickers) throws Exception {
        return imdb.findSeriesByTitle(title, stickers);
    }

}
