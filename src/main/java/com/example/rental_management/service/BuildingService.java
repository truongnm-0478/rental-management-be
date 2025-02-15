package com.example.rental_management.service;

import com.example.rental_management.entity.Building;

import java.util.List;
import java.util.Optional;

public interface BuildingService {
    List<Building> getAllBuildings();

    Building getBuildingById(Long id);
}
