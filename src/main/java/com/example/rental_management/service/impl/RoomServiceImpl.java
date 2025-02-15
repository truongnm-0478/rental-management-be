package com.example.rental_management.service.impl;

import com.example.rental_management.dto.response.RoomResponse;
import com.example.rental_management.entity.Image;
import com.example.rental_management.entity.Room;
import com.example.rental_management.repository.RoomRepository;
import com.example.rental_management.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    private RoomRepository roomRepository;

//    public List<RoomResponse> getAllRooms() {
//        List<Room> rooms = roomRepository.findByDeletedAtIsNullAndBuilding_DeletedAtIsNull();
//
//        return rooms.stream().map(room -> {
//            RoomResponse response = new RoomResponse();
//            response.setId(room.getId());
//            response.setRoomNumber(room.getName());
//            response.setType(room.getType());
//            response.setAddress(room.getBuilding().getAddress());
//            response.setBuilding(room.getBuilding().getName());
//            response.setShortPrice(room.getShortPrice());
//            response.setMidPrice(room.getMidPrice());
//            response.setStatus(room.getStatus());
//
//            List<String> imageUrls = room.getImages().stream()
//                    .map(Image::getUrl)
//                    .collect(Collectors.toList());
//            response.setImages(imageUrls);
//
//            return response;
//        }).collect(Collectors.toList());
//    }

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

    public Optional<Room> getRoomById(Long id) {
        return roomRepository.findById(id);
    }

    public Room saveRoom(Room room) {
        return roomRepository.save(room);
    }

    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }
}
