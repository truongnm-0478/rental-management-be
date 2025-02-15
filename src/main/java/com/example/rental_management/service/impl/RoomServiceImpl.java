package com.example.rental_management.service.impl;

import com.example.rental_management.dto.response.RoomResponse;
import com.example.rental_management.entity.Image;
import com.example.rental_management.entity.Room;
import com.example.rental_management.repository.RoomRepository;
import com.example.rental_management.service.RoomService;
import com.example.rental_management.util.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    @Autowired
    private RoomRepository roomRepository;

    public Page<RoomResponse> getAllRooms(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Room> roomsPage = roomRepository.findByDeletedAtIsNullAndBuilding_DeletedAtIsNull(pageable);

        return roomsPage.map(room -> {
            RoomResponse response = new RoomResponse();
            response.setId(room.getId());
            response.setRoomNumber(room.getName());
            response.setType(room.getType());
            response.setAddress(room.getBuilding().getAddress());
            response.setBuilding(room.getBuilding().getName());
            response.setShortPrice(room.getShortPrice());
            response.setMidPrice(room.getMidPrice());
            response.setStatus(room.getStatus());

            List<String> imageUrls = room.getImages().stream()
                    .map(Image::getUrl)
                    .collect(Collectors.toList());
            response.setImages(imageUrls);
            return response;
        });
    }

    public RoomResponse getRoomById(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with ID: " + id));

        RoomResponse response = new RoomResponse();
        response.setId(room.getId());
        response.setRoomNumber(room.getName());
        response.setType(room.getType());
        response.setAddress(room.getBuilding().getAddress());
        response.setBuilding(room.getBuilding().getName());
        response.setShortPrice(room.getShortPrice());
        response.setMidPrice(room.getMidPrice());
        response.setStatus(room.getStatus());

        List<String> imageUrls = room.getImages().stream()
                .map(Image::getUrl)
                .collect(Collectors.toList());
        response.setImages(imageUrls);

        return response;
    }

    public Room saveRoom(Room room) {
        return roomRepository.save(room);
    }

    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }
}
