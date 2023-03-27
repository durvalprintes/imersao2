package com.imersao.spring.imdb;

import com.imersao.spring.sticker.Content;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ImdbService implements ImdbApi {

    @Autowired
    private ImdbClient imdb;


    public List<Content> getTop250Movies() throws Exception {
        return consumeItems(imdb.getTop250Movies());
    }

    public List<Content> getTop250Tvs() throws Exception {
        return consumeItems(imdb.getTop250Tvs());
    }

    public List<Content> findMoviesByTitle(String title) throws Exception {
        List<Content> contents = consumeResults(imdb.findMoviesByTitle(title));
        addRating(contents);
        return contents;
    }

    public List<Content> findSeriesByTitle(String title) throws Exception {
        List<Content> contents = consumeResults(imdb.findSeriesByTitle(title));
        addRating(contents);
        return contents;
    }

    private void addRating(List<Content> contents) {
        contents.forEach(content -> {
            try {
                content.setRating(imdb.findRatingsById(content.getId()).getImDb());
            } catch (Exception e) {
                log.warn("Error Rating {}", content.getId());
            }
        });
    }

}
