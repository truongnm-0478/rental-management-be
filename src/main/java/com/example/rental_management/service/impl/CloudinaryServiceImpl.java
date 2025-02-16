package com.example.rental_management.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.rental_management.repository.BuildingRepository;
import com.example.rental_management.repository.ImageRepository;
import com.example.rental_management.repository.RoomRepository;
import com.example.rental_management.service.CloudinaryService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String uploadImage(MultipartFile file) {
        try{
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            return uploadResult.get("url").toString();
        } catch (IOException e) {
            throw new RuntimeException("Error when upload to Cloudinary", e);
        }
    }

    public void deleteImage(String imageUrl) {
        try {
            String publicId = extractPublicId(imageUrl);

            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        } catch (Exception e) {
            throw new RuntimeException("Error when deleting image from Cloudinary", e);
        }
    }

    private String extractPublicId(String imageUrl) {
        String[] parts = imageUrl.split("/");
        String publicIdWithExtension = parts[parts.length - 1];

        return publicIdWithExtension.substring(0, publicIdWithExtension.lastIndexOf('.'));
    }
}
