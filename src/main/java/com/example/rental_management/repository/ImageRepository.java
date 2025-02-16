package com.example.rental_management.repository;

import com.example.rental_management.entity.Building;
import com.example.rental_management.entity.Image;
import com.example.rental_management.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByRoom(Room room);
}
