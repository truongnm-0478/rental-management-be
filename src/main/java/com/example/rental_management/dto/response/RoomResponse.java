package com.example.rental_management.dto.response;

import com.example.rental_management.entity.Room;
import com.example.rental_management.entity.enums.RoomStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomResponse {
    private Long id;
    private String roomNumber;
    private String type;
    private String address;
    private String building;
    private double shortPrice;
    private double midPrice;
    private RoomStatus status;
    private List<String> images;
}
