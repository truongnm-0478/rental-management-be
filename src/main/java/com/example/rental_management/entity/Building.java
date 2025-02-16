package com.example.rental_management.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "building")
public class Building {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    private int numberOfRooms;

    @Column(nullable = false)
    private double midPrice = 0.0;

    @Column(nullable = false)
    private double shortPrice = 0.0;

    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL)
    private List<Room> rooms;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt = LocalDateTime.now();;

    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    @PreUpdate
    private void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
