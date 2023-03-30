package com.imersao.spring.client.imdb;

import com.imersao.spring.client.KafkaClient;
import com.imersao.spring.sticker.Content;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImdbService implements ImdbApi {

    private final ImdbClient imdb;

    private final KafkaClient stickerGenerator;

    public List<Content> getTop250Movies(Integer limit) throws Exception {
        List<Content> contents = consumeItems(imdb.getTop250Movies());
        startStickerGenerator(contents, limit);
        return contents;
    }

    public List<Content> getTop250Tvs(Integer limit) throws Exception {
        List<Content> contents = consumeItems(imdb.getTop250Tvs());
        startStickerGenerator(contents, limit);
        return contents;
    }

    public List<Content> getMostPopularMovies(Integer limit) throws Exception {
        List<Content> contents = consumeItems(imdb.getMostPopularMovies());
        startStickerGenerator(contents, limit);
        return contents;
    }

    public List<Content> getMostPopularTvs(Integer limit) throws Exception {
        List<Content> contents = consumeItems(imdb.getMostPopularTvs());
        startStickerGenerator(contents, limit);
        return contents;
    }

    public List<Content> findMoviesByTitle(String title, Integer limit) throws Exception {
        List<Content> contents = consumeResults(imdb.findMoviesByTitle(title));
        startStickerGenerator(contents, limit);
        addRating(contents);
        return contents;
    }

    public List<Content> findSeriesByTitle(String title, Integer limit) throws Exception {
        List<Content> contents = consumeResults(imdb.findSeriesByTitle(title));
        startStickerGenerator(contents, limit);
        addRating(contents);
        return contents;

    }

    private void startStickerGenerator(List<Content> contents, Integer limit) throws Exception {
        stickerGenerator.sendMessage(contents.subList(0, (limit > contents.size() ? contents.size() : limit)));
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
