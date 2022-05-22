package com.example.DriveWithMe.dto;

public class RideRequestDTO {
    private Long rideId;
    private String userEmail;

    public RideRequestDTO() {}

    public RideRequestDTO(Long rideId, String userEmail) {
        this.rideId = rideId;
        this.userEmail = userEmail;
    }

    public Long getRideId() {
        return rideId;
    }

    public void setRideId(Long rideId) {
        this.rideId = rideId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
