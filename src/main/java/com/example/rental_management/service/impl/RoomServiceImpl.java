package com.example.rental_management.service.impl;

import com.example.rental_management.dto.request.RoomRequest;
import com.example.rental_management.dto.response.RoomResponse;
import com.example.rental_management.entity.Building;
import com.example.rental_management.entity.Image;
import com.example.rental_management.entity.Room;
import com.example.rental_management.entity.enums.RoomStatus;
import com.example.rental_management.repository.BuildingRepository;
import com.example.rental_management.repository.ImageRepository;
import com.example.rental_management.repository.RoomRepository;
import com.example.rental_management.service.CloudinaryService;
import com.example.rental_management.service.RoomService;
import com.example.rental_management.util.exception.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final BuildingRepository buildingRepository;
    private final ImageRepository imageRepository;
    private final CloudinaryService cloudinaryService;

    public RoomServiceImpl(RoomRepository roomRepository, BuildingRepository buildingRepository, ImageRepository imageRepository, CloudinaryService cloudinaryService) {
        this.roomRepository = roomRepository;
        this.buildingRepository = buildingRepository;
        this.imageRepository = imageRepository;
        this.cloudinaryService = cloudinaryService;
    }

    @Override
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

    @Override
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

    @Override
    public Room createRoom(RoomRequest roomRequest) {
        // Find building
        Optional<Building> optionalBuilding = buildingRepository.findByName(roomRequest.getBuilding());
        Building building;

        if (optionalBuilding.isEmpty()) {
            building = Building.builder()
                    .name(roomRequest.getBuilding())
                    .address(roomRequest.getAddress())
                    .numberOfRooms(1)
                    .build();
            building = buildingRepository.save(building);
        } else {
            building = optionalBuilding.get();
        }

        // Add new room
        Room room = Room.builder()
                .name(roomRequest.getRoomNumber())
                .type(roomRequest.getType())
                .midPrice(roomRequest.getMidPrice() != null ? roomRequest.getMidPrice() : 0.0)
                .shortPrice(roomRequest.getShortPrice() != null ? roomRequest.getShortPrice() : 0.0)
                .area(roomRequest.getArea() != null ? roomRequest.getArea() : 0.0)
                .status(roomRequest.getStatus() != null ? roomRequest.getStatus() : RoomStatus.AVAILABLE)
                .building(building)
                .build();
        Room savedRoom = roomRepository.save(room);

        // Update number of room on Building
        building.setNumberOfRooms(building.getNumberOfRooms() + 1);
        buildingRepository.save(building);

        // Upload to Cloudinary and save to DB
        if (roomRequest.getImage() != null && !roomRequest.getImage().isEmpty()) {
            try {
                String imageUrl = cloudinaryService.uploadImage(roomRequest.getImage());
                Image image = new Image();
                image.setRoom(savedRoom);
                image.setUrl(imageUrl);
                imageRepository.save(image);
            } catch (Exception e) {
                throw new RuntimeException("Error when upload to Cloudinary", e);
            }
        }

        return savedRoom;
    }

    @Override
    public void deleteRoom(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Room does not exits"));

        room.setDeletedAt(LocalDateTime.now());
        roomRepository.save(room);
    }
}
