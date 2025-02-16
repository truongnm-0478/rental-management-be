package com.example.rental_management.repository;

import com.example.rental_management.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByStatus(String status);
    Page<Room> findByDeletedAtIsNullAndBuilding_DeletedAtIsNull(Pageable pageable);

    @Query("SELECT r FROM Room r WHERE " +
            "(LOWER(r.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(r.building.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(r.type) LIKE LOWER(CONCAT('%', :search, '%'))) " +
            "AND r.deletedAt IS NULL AND r.building.deletedAt IS NULL")
    Page<Room> searchRooms(@Param("search") String search, Pageable pageable);

}
