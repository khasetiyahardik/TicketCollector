package com.example.TicketCollector;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.awt.image.BufferedImage;

@SpringBootApplication
@EnableSwagger2
public class TicketCollectorApplication {

    public static void main(String[] args) {
        SpringApplication.run(TicketCollectorApplication.class, args);
    }

    @Bean
    public HttpMessageConverter<BufferedImage> createImageHttpMessageConverter() {
        return new BufferedImageHttpMessageConverter();
    }
    @Bean
    public Cloudinary cloudinary() {
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dw4dtqwmi",
                "api_key", "153533792575435",
                "api_secret", "gCMp3wgRMONDo5eA6iqoILirhLc",
                "secure", true));
        return cloudinary;
    }
}
