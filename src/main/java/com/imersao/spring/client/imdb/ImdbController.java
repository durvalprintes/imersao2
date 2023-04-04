package com.imersao.spring.client.imdb;

import com.imersao.spring.sticker.Content;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/imdb")
@RequiredArgsConstructor
public class ImdbController {

    private final ImdbService imdb;

    @GetMapping("/top-250-movies")
    public String getTop250Movies(
            Model view,
            @RequestParam(name = "stickers", defaultValue = "5", required = false) Integer stickers,
            @RequestParam(name = "message", defaultValue = "JAVA", required = false) String message) throws Exception {
        return loadView(view, imdb.getTop250Movies(stickers, message));
    }

    @GetMapping("/top-250-tvs")
    public String getTop250Tvs(
            Model view,
            @RequestParam(name = "stickers", defaultValue = "5", required = false) Integer stickers,
            @RequestParam(name = "message", defaultValue = "JAVA", required = false) String message) throws Exception {
        return loadView(view, imdb.getTop250Tvs(stickers, message));
    }

    @GetMapping("/most-popular-movies")
    public String getMostPopularMovies(
            Model view,
            @RequestParam(name = "stickers", defaultValue = "5", required = false) Integer stickers,
            @RequestParam(name = "message", defaultValue = "JAVA", required = false) String message) throws Exception {
        return loadView(view, imdb.getMostPopularMovies(stickers, message));
    }

    @GetMapping("/most-popular-tvs")
    public String getMostPopularTvs(
            Model view,
            @RequestParam(name = "stickers", defaultValue = "5", required = false) Integer stickers,
            @RequestParam(name = "message", defaultValue = "JAVA", required = false) String message) throws Exception {
        return loadView(view, imdb.getMostPopularTvs(stickers, message));
    }

    @GetMapping("/movies/{title}")
    public String findMoviesByTitle(
            Model view,
            @PathVariable("title") String title,
            @RequestParam(name = "stickers", defaultValue = "5", required = false) Integer stickers,
            @RequestParam(name = "message", defaultValue = "JAVA", required = false) String message) throws Exception {
        return loadView(view, imdb.findMoviesByTitle(title, stickers, message));
    }

    @GetMapping("/series/{title}")
    public String findSeriesByTitle(
            Model view,
            @PathVariable("title") String title,
            @RequestParam(name = "stickers", defaultValue = "5", required = false) Integer stickers,
            @RequestParam(name = "message", defaultValue = "JAVA", required = false) String message) throws Exception {
        return loadView(view, imdb.findSeriesByTitle(title, stickers, message));
    }

    @GetMapping("/stickers")
    @ResponseBody
    public List<String> getGeneratedStickers() {
        return imdb.getGeneratedStickers();
    }

    private String loadView(Model view, List<Content> contentList) {
        view.addAttribute("title", "Lista de objetos IMDB");
        view.addAttribute("contentList", contentList);
        return "index";
    }

}
