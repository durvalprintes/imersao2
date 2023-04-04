package com.imersao.spring.client.kafka;

import com.imersao.spring.sticker.Content;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KafkaMessage {
    List<Content> stickers;
    String message;
}
