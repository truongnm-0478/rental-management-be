package com.example.rental_management.controller;

import com.example.rental_management.dto.response.RoomResponse;
import com.example.rental_management.entity.Room;
import com.example.rental_management.entity.enums.RoomStatus;
import com.example.rental_management.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {
    @Autowired
    private RoomService roomService;

    // Get all room
    @GetMapping
    public Page<RoomResponse> getAllRooms(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return roomService.getAllRooms(page, size);
    }

    // Get room by id
    @GetMapping("/{id}")
    public ResponseEntity<RoomResponse> getRoomById(@PathVariable Long id) {
        RoomResponse room = roomService.getRoomById(id);
        return ResponseEntity.ok(room);
    }

    // Create room
    @PostMapping
    public ResponseEntity<Room> createRoom(@RequestBody Room room) {
        if (room.getStatus() != RoomStatus.AVAILABLE && room.getStatus() != RoomStatus.RENTED) {
            return ResponseEntity.badRequest().build(); // Trả về lỗi 400 mà không cần `body(null)`
        }
        Room savedRoom = roomService.saveRoom(room);
        return ResponseEntity.ok(savedRoom);
    }

    // Update room
//    @PutMapping("/{id}")
//    public ResponseEntity<Room> updateRoom(@PathVariable Long id, @RequestBody Room roomDetails) {
//        Optional<Room> optionalRoom = roomService.getRoomById(id);
//        if (optionalRoom.isPresent()) {
//            Room room = optionalRoom.get();
//            room.setName(roomDetails.getName());
//            room.setPrice(roomDetails.getPrice());
//            room.setArea(roomDetails.getArea());
//            room.setStatus(roomDetails.getStatus());
//            return ResponseEntity.ok(roomService.saveRoom(room));
//        }
//        return ResponseEntity.notFound().build();
//    }

    // Delete room
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }
}
