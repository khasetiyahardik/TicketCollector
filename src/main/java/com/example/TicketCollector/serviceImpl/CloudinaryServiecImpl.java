package com.example.TicketCollector.serviceImpl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.TicketCollector.service.CloudinaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Map;
import java.util.UUID;


@Service
@Slf4j
public class CloudinaryServiecImpl implements CloudinaryService {
    @Autowired
    Cloudinary cloudinary;

    public String uploadFileOnCloudinary(byte[] pdfDatafile) throws IOException {
        log.info("FileUploadServiceImpl :: uploadFileOnCloudinary :: uploading...");
        File tempFile = File.createTempFile("temp", ".pdf");
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(pdfDatafile);
            Map uploadResult = cloudinary.uploader().upload(tempFile, ObjectUtils.emptyMap());
            log.info("FileUploadServiceImpl :: uploadFileOnCloudinary :: uploaded...");
            return uploadResult.get("secure_url").toString();
        } finally {
            new File(tempFile.toURI()).delete();
        }
    }

    public String uploadImageToCloudinary(BufferedImage image) throws IOException {
        log.info("FileUploadServiceImpl :: uploadImageToCloudinary :: uploading...");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        byte[] imageData = baos.toByteArray();

        Map uploadResult = cloudinary.uploader().upload(imageData, ObjectUtils.emptyMap());
        log.info("FileUploadServiceImpl :: uploadImageToCloudinary :: uploaded...");
        return uploadResult.get("secure_url").toString();
    }

}
