package com.imersao.spring.imdb;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imersao.spring.exception.ClientException;
import com.imersao.spring.sticker.Content;

import java.util.List;
import java.util.stream.StreamSupport;

public interface ImdbApi {

    default List<Content> consumeItems(String json) throws Exception {
        return StreamSupport
                .stream(checkJson(json).get("items").spliterator(), false)
                .map(mapper ->  Content.builder()
                                .title(mapper.get("title").asText())
                                .urlImage(mapper.get("image").asText())
                                .rating(mapper.get("imDbRating").asText())
                                .build())
                .toList();
    }

    default List<Content> consumeResults(String json) throws Exception {
        return StreamSupport
                .stream(checkJson(json).get("results").spliterator(), false)
                .map(node ->
                        Content.builder()
                                .id(node.get("id").asText())
                                .title(node.get("title").asText())
                                .urlImage(node.get("image").asText()).build())
                .toList();
    }

    default JsonNode checkJson(String json) throws Exception {
        var jsonMapper = new ObjectMapper().readTree(json);
        var errorMessage = jsonMapper.get("errorMessage").asText();
        if (!errorMessage.isBlank()) throw new ClientException("Dados indisponíveis: " + errorMessage);
        return jsonMapper;
    }

}
