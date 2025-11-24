package com.branches.utils;

import com.branches.exception.InternalServerError;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Base64;

@Component
public class CompressImage {
    public byte[] execute(String imageBase64, int maxWidth, int maxHeight, double percentageQuality, ImageOutPutFormat outputFormat) {
        try {
            String formattedImage = imageBase64.substring(imageBase64.indexOf(",") + 1);

            byte[] imageBytes = Base64.getDecoder().decode(formattedImage);

            BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(imageBytes));

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Thumbnails.of(originalImage)
                    .size(maxWidth, maxHeight)
                    .outputFormat(outputFormat.getFormat())
                    .outputQuality(percentageQuality)
                    .toOutputStream(outputStream);

            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new InternalServerError("Ocorreu um erro ao comprimir a imagem");
        }
    }
}
