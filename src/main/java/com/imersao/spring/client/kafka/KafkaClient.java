package com.imersao.spring.client.kafka;

import com.imersao.spring.sticker.Content;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KafkaClient {

    @Value(value = "${topic.sticker}")
    private String topic;

    private final KafkaTemplate<String, KafkaMessage> kafkaTemplate;

    public void sendMessage(List<Content> contentList, String message) {
        kafkaTemplate.send(topic, new KafkaMessage(contentList, message));
    }
}
