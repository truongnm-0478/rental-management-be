package com.example.rental_management.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Building {
    @Id
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("address")
    private String address;

    @JsonProperty("numberOfRooms")
    private int numberOfRooms;

    @JsonProperty("price")
    private double price;

    @Override
    public String toString() {
        return "Building{id=" + id + ", name='" + name + "', address='" + address + "'}";
    }

}
