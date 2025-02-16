package com.example.rental_management.repository;

import com.example.rental_management.entity.Building;
import com.example.rental_management.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
