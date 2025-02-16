package com.example.rental_management.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

public interface CloudinaryService {
    String uploadImage(MultipartFile file);

    void deleteImage(String imageUrl);
}
