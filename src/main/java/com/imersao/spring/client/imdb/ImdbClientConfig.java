package com.imersao.spring.client.imdb;

import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class ImdbClientConfig {

    @Value("${imdb.url}")
    private String urlBase;

    @Value("${imdb.key}")
    private String apiKey;

    @Bean
    public RequestInterceptor addKey() {
        return template -> log.debug("Request {}{}",
                urlBase,
                template.url().contains("Search") || template.url().contains("Ratings")
                        ? template.uri(
                                template.url()
                                        .replaceAll("(./)(.)", "$1".concat(apiKey.concat("/$2")))).url()
                        : template.uri(apiKey, true).url());
    }

}
