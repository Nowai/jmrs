package de.rieki.jmrs.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.MemoryCacheImageInputStream;
import javax.persistence.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;

@Data
@Entity
@NoArgsConstructor
public class Artwork {

    public static enum Size {
        SIZE_500,
        SIZE_100,
        SIZE_50
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private byte[] cover500x500;

    @Lob
    private byte[] cover100x100;

    @Lob
    private byte[] cover50x50;

    @OneToOne
    private Song song;

    public Artwork(byte[] cover, String mimeType) {
        // get
        Iterator<ImageReader> iImageReader = ImageIO.getImageReadersByMIMEType(mimeType);
        ImageReader imageReader;
        if(iImageReader.hasNext())
           imageReader = iImageReader.next();
        else
            return;
        MemoryCacheImageInputStream inputStream = new MemoryCacheImageInputStream(new ByteArrayInputStream(cover));
        imageReader.setInput(inputStream);
        BufferedImage bufferedImage;
        try {
            bufferedImage = imageReader.read(0);
        } catch (IOException e) {
            return;
        }
        this.cover500x500 = this.bufferedImageToByteArray(Scalr.resize(bufferedImage,500));
        this.cover100x100 = this.bufferedImageToByteArray(Scalr.resize(bufferedImage, 100));
        this.cover50x50 = this.bufferedImageToByteArray(Scalr.resize(bufferedImage, 50));
    }

    private byte[] bufferedImageToByteArray(BufferedImage bufferedImage) {
        byte[] image;
        try(ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            ImageIO.write(bufferedImage, "jpg", byteArrayOutputStream);
            byteArrayOutputStream.flush();
            image = byteArrayOutputStream.toByteArray();
        }catch(IOException e) {
            return null;
        }
        return image;
    }

}
