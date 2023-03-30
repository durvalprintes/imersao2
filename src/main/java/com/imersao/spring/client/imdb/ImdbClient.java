package com.imersao.spring.client.imdb;

import com.imersao.spring.sticker.Rating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "imdb", url = "${imdb.url}", configuration = ImdbClientConfig.class)
public interface ImdbClient {

    @GetMapping("Top250Movies")
    String getTop250Movies();

    @GetMapping("Top250TVs")
    String getTop250Tvs();

    @GetMapping("SearchMovie/{title}")
    String findMoviesByTitle(@PathVariable("title") String title);

    @GetMapping("SearchSeries/{title}")
    String findSeriesByTitle(@PathVariable("title") String title);

    @GetMapping("Ratings/{id}")
    Rating findRatingsById(@PathVariable("id") String id);

}
