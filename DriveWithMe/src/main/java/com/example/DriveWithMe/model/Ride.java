package com.example.DriveWithMe.model;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "rides")
public class Ride {
    @Id
    @GeneratedValue
    private Long id;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "country", column = @Column(name = "starting_point_country")),
            @AttributeOverride(name = "city", column = @Column(name = "starting_point_city")),
            @AttributeOverride(name = "address", column = @Column(name = "starting_point_address"))
    })
    private Location startingPoint;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "country", column = @Column(name = "destination_country")),
            @AttributeOverride(name = "city", column = @Column(name = "destination_city")),
            @AttributeOverride(name = "address", column = @Column(name = "destination_address"))
    })
    private Location destination;
    @NotNull
    @ManyToOne
    private User driver;
    private int maxPassengers;
    @NotNull
    @ManyToMany
    private List<User> passengers;
    @NotNull
    @ManyToMany
    private List<User> requests;
    @Embedded
    private Price price;
    private String rules;

    public Ride() {}

    public Ride(Long id, LocalDateTime departureTime, LocalDateTime arrivalTime, Location startingPoint, Location destination, User driver, int maxPassengers, List<User> passengers, List<User> requests, Price price, String rules) {
        this.id = id;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.startingPoint = startingPoint;
        this.destination = destination;
        this.driver = driver;
        this.maxPassengers = maxPassengers;
        this.passengers = passengers;
        this.requests = requests;
        this.price = price;
        this.rules = rules;
    }

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

    public User getDriver() {
        return driver;
    }

    public void setDriver(User driver) {
        this.driver = driver;
    }

    public int getMaxPassengers() {
        return maxPassengers;
    }

    public void setMaxPassengers(int maxPassengers) {
        this.maxPassengers = maxPassengers;
    }

    public List<User> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<User> passengers) {
        this.passengers = passengers;
    }

    public List<User> getRequests() {
        return requests;
    }

    public void setRequests(List<User> requests) {
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
