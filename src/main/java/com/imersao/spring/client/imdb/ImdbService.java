package com.imersao.spring.client.imdb;

import com.imersao.spring.client.kafka.KafkaClient;
import com.imersao.spring.sticker.Content;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImdbService implements ImdbApi {

    private final ImdbClient imdb;

    private final KafkaClient stickerGenerator;

    public List<Content> getTop250Movies(Integer limit, String message) throws Exception {
        List<Content> contents = consumeItems(imdb.getTop250Movies());
        startStickerGenerator(contents, limit, message);
        return contents;
    }

    public List<Content> getTop250Tvs(Integer limit, String message) throws Exception {
        List<Content> contents = consumeItems(imdb.getTop250Tvs());
        startStickerGenerator(contents, limit, message);
        return contents;
    }

    public List<Content> getMostPopularMovies(Integer limit, String message) throws Exception {
        List<Content> contents = consumeItems(imdb.getMostPopularMovies());
        startStickerGenerator(contents, limit, message);
        return contents;
    }

    public List<Content> getMostPopularTvs(Integer limit, String message) throws Exception {
        List<Content> contents = consumeItems(imdb.getMostPopularTvs());
        startStickerGenerator(contents, limit, message);
        return contents;
    }

    public List<Content> findMoviesByTitle(String title, Integer limit, String message) throws Exception {
        List<Content> contents = consumeResults(imdb.findMoviesByTitle(title));
        startStickerGenerator(contents, limit, message);
        addRating(contents);
        return contents;
    }

    public List<Content> findSeriesByTitle(String title, Integer limit, String message) throws Exception {
        List<Content> contents = consumeResults(imdb.findSeriesByTitle(title));
        startStickerGenerator(contents, limit, message);
        addRating(contents);
        return contents;

    }

    private void startStickerGenerator(List<Content> contents, Integer limit, String message) {
        stickerGenerator.sendMessage(contents.subList(0, (limit > contents.size() ? contents.size() : limit)), message);
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

    @CacheEvict(value = "stickers", allEntries = true)
    public List<String> getGeneratedStickers() {
        try {
            File stickers = new File("src/main/resources/sticker/imdb/");
            if (stickers.exists())
                return Arrays.stream(Objects.requireNonNull(stickers.listFiles()))
                        .map(sticker -> ServletUriComponentsBuilder
                                .fromCurrentContextPath()
                                .path("/sticker/imdb/")
                                .path(sticker.getName())
                                .toUriString())
                        .toList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
