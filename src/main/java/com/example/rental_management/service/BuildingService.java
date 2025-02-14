package com.example.rental_management.service;

import com.example.rental_management.model.Building;
import com.example.rental_management.repository.BuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BuildingService {
    @Autowired
    private BuildingRepository buildingRepository;

    public List<Building> getAllBuildings() {
        List<Building> buildings = buildingRepository.findAll();
        System.out.println("Danh sách các tòa nhà: " + buildings);
        return buildings;
    }


    public Building getBuildingById(Long id) {
        Optional<Building> building = buildingRepository.findById(id);
        return building.orElse(null);
    }
}
