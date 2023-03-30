package com.imersao.spring.client.imdb;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imersao.spring.sticker.Content;
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
import java.util.List;

@Slf4j
@Service
public class ImdbListener {

    @KafkaListener(topics = "${topic.sticker}", groupId = "group")
    public void listenGeneratorStickers(String contents) throws Exception {
        log.info("Generate Stickers...");
        List<Content> contentList = new ObjectMapper().readValue(contents, new TypeReference<>() {
        });
        contentList.forEach(content -> {
            try {
                log.info("Title {}...", content.getTitle());

                createSticker(
                        content.getTitle().replaceAll("\\W","_"),
                        new URL(content.getUrlImage().replaceAll("\\._(.+).jpg$", ".jpg"))
                                .openStream());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        log.info("Generate Ok");
    }

    private void createSticker(String title, InputStream image) throws Exception {
        final String formatSticker = "png";
        final String outputPath = "data/image/sticker/imdb/";

        BufferedImage original = ImageIO.read(image);

        final int width = original.getWidth();
        final int height = original.getHeight();

        BufferedImage sticker = new BufferedImage(width, height + 200, Transparency.TRANSLUCENT);

        Graphics2D graphic = (Graphics2D) sticker.getGraphics();
        graphic.drawImage(original, 0, 0, width, height, null);

        var font = new Font("Comic Sans MS", Font.BOLD, 128);

        Shape textShape = new TextLayout("JAVA", font, graphic.getFontRenderContext()).getOutline(null);

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
