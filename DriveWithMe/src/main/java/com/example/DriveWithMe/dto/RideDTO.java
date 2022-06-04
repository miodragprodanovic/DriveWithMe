package com.example.DriveWithMe.dto;

import com.example.DriveWithMe.model.Location;
import com.example.DriveWithMe.model.Price;

import java.time.LocalDateTime;
import java.util.List;

public class RideDTO {
    private Long id;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private Location startingPoint;
    private Location destination;
    private UserDTO driver;
    private int maxPassengers;
    private List<UserDTO> passengers;
    private List<UserDTO> requests;
    private Price price;
    private String rules;

    public RideDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Location getStartingPoint() {
        return startingPoint;
    }

    public void setStartingPoint(Location startingPoint) {
        this.startingPoint = startingPoint;
    }

    public Location getDestination() {
        return destination;
    }

    public void setDestination(Location destination) {
        this.destination = destination;
    }

    public UserDTO getDriver() {
        return driver;
    }

    public void setDriver(UserDTO driver) {
        this.driver = driver;
    }

    public int getMaxPassengers() {
        return maxPassengers;
    }

    public void setMaxPassengers(int maxPassengers) {
        this.maxPassengers = maxPassengers;
    }

    public List<UserDTO> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<UserDTO> passengers) {
        this.passengers = passengers;
    }

    public List<UserDTO> getRequests() {
        return requests;
    }

    public void setRequests(List<UserDTO> requests) {
        this.requests = requests;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }
}
