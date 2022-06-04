package com.example.DriveWithMe.adapter;

import com.example.DriveWithMe.dto.RideDTO;
import com.example.DriveWithMe.dto.UserDTO;
import com.example.DriveWithMe.model.Ride;
import com.example.DriveWithMe.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RideAdapter {
    @Autowired
    private final UserAdapter userAdapter;

    public RideAdapter(UserAdapter userAdapter) {
        this.userAdapter = userAdapter;
    }

    public Ride RideDTOToRide(RideDTO rideDTO) {
        Ride ride = new Ride();

        ride.setId(rideDTO.getId());
        ride.setDepartureTime(rideDTO.getDepartureTime());
        ride.setArrivalTime(rideDTO.getArrivalTime());
        ride.setStartingPoint(rideDTO.getStartingPoint());
        ride.setDestination(rideDTO.getDestination());
        ride.setDriver(this.userAdapter.UserDTOToUser(rideDTO.getDriver()));
        ride.setMaxPassengers(rideDTO.getMaxPassengers());
        ride.setPassengers(new ArrayList<>());
        ride.setRequests(new ArrayList<>());
        ride.setPrice(rideDTO.getPrice());
        ride.setRules(rideDTO.getRules());

        for (UserDTO userDTO : rideDTO.getPassengers()) {
            ride.getPassengers().add(this.userAdapter.UserDTOToUser(userDTO));
        }

        for (UserDTO userDTO : rideDTO.getRequests()) {
            ride.getPassengers().add(this.userAdapter.UserDTOToUser(userDTO));
        }

        return ride;
    }

    public RideDTO RideToRideDTO(Ride ride) {
        RideDTO rideDTO = new RideDTO();

        rideDTO.setId(ride.getId());
        rideDTO.setDepartureTime(ride.getDepartureTime());
        rideDTO.setArrivalTime(ride.getArrivalTime());
        rideDTO.setStartingPoint(ride.getStartingPoint());
        rideDTO.setDestination(ride.getDestination());
        rideDTO.setDriver(this.userAdapter.UserToUserDTO(ride.getDriver()));
        rideDTO.setMaxPassengers(ride.getMaxPassengers());
        rideDTO.setPassengers(new ArrayList<>());
        rideDTO.setRequests(new ArrayList<>());
        rideDTO.setPrice(ride.getPrice());
        rideDTO.setRules(ride.getRules());

        for (User user : ride.getPassengers()) {
            rideDTO.getPassengers().add(this.userAdapter.UserToUserDTO(user));
        }

        for (User user : ride.getRequests()) {
            rideDTO.getRequests().add(this.userAdapter.UserToUserDTO(user));
        }

        return rideDTO;
    }

    public List<Ride> RideDTOsToRides(List<RideDTO> rideDTOs) {
        List<Ride> rides = new ArrayList<>();

        for (RideDTO rideDTO : rideDTOs) {
            rides.add(RideDTOToRide(rideDTO));
        }

        return rides;
    }

    public List<RideDTO> RidesToRideDTOs(List<Ride> rides) {
        List<RideDTO> rideDTOs = new ArrayList<>();

        for (Ride ride : rides) {
            rideDTOs.add(RideToRideDTO(ride));
        }

        return rideDTOs;
    }
}
