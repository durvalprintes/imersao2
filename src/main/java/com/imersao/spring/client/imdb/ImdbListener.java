package com.imersao.spring.client.imdb;

import com.imersao.spring.client.kafka.KafkaMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
@Service
public class ImdbListener {

    @KafkaListener(topics = "${topic.sticker}", groupId = "group")
    public void listenGeneratorStickers(KafkaMessage contents) {
        log.info("Generate Stickers...");
        contents.getStickers().forEach(sticker -> {
            try {
                log.info("Title {}...", sticker.getTitle());

                createSticker(
                        sticker.getTitle().replaceAll("\\W", "_"),
                        new URL(sticker.getUrlImage().replaceAll("\\._(.+).jpg$", ".jpg"))
                                .openStream(),
                        contents.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        log.info("Generate Ok");
    }

    private void createSticker(String title, InputStream image, String message) throws Exception {
        final String formatSticker = "png";
        final String outputPath = "src/main/resources/sticker/imdb/";

        BufferedImage original = ImageIO.read(image);

        final int width = original.getWidth();
        final int height = original.getHeight();

        BufferedImage sticker = new BufferedImage(width, height + 200, Transparency.TRANSLUCENT);

        Graphics2D graphic = (Graphics2D) sticker.getGraphics();
        graphic.drawImage(original, 0, 0, width, height, null);

        var font = new Font("IMPACT", Font.BOLD, 128);

        Shape textShape = new TextLayout(message, font, graphic.getFontRenderContext()).getOutline(null);

        graphic.translate(
                (sticker.getWidth() - textShape.getBounds().width) / 2,
                ((200 - textShape.getBounds().height) / 2) + textShape.getBounds().height + height);
        graphic.setColor(Color.BLACK);
        graphic.fill(textShape);
        graphic.setStroke(new BasicStroke(5F));
        graphic.setColor(Color.YELLOW);
        graphic.draw(textShape);

        Files.createDirectories(Paths.get(outputPath));
        ImageIO.write(sticker, formatSticker, new File(outputPath + title + "." + formatSticker));
    }

}
