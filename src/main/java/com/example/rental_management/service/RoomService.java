package com.example.rental_management.service;

import com.example.rental_management.dto.request.RoomRequest;
import com.example.rental_management.dto.response.RoomResponse;
import com.example.rental_management.entity.Room;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface RoomService {
    Page<RoomResponse> getAllRooms(int page, int size, String search);

    RoomResponse getRoomById(Long id);

    Room createRoom(RoomRequest roomRequest);

    void deleteRoom(Long id);

    RoomResponse updateRoom(Long id, RoomRequest roomRequest);
}
