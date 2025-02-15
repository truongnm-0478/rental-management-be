package com.example.rental_management.repository;

import com.example.rental_management.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByStatus(String status);
    Page<Room> findByDeletedAtIsNullAndBuilding_DeletedAtIsNull(Pageable pageable);
}
