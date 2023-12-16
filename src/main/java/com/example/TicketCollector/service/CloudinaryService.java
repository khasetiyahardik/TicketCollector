package com.example.TicketCollector.service;

import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public interface CloudinaryService {
     String uploadFileOnCloudinary(byte[] file) throws IOException;
     String uploadImageToCloudinary(BufferedImage image) throws IOException;
}
