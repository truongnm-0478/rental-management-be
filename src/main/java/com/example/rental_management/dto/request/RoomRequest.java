package com.example.rental_management.dto.request;

import com.example.rental_management.entity.enums.RoomStatus;
import lombok.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomRequest {
    private String roomNumber;
    private String type;
    private String address;
    private String building;
    private Double shortPrice;
    private Double midPrice;
    private Double area;
    private RoomStatus status;
    private MultipartFile image;
}
