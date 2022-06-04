package com.example.DriveWithMe.service;

import com.example.DriveWithMe.dto.RideRequestDTO;
import com.example.DriveWithMe.model.Ride;
import com.example.DriveWithMe.model.User;
import com.example.DriveWithMe.repository.RideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class RideService {
    @Autowired
    private final UserService userService;
    @Autowired
    private final RideRepository rideRepository;

    public RideService(UserService userService, RideRepository rideRepository) {
        this.userService = userService;
        this.rideRepository = rideRepository;
    }

    public List<Ride> getRides() {
        return rideRepository.findAll();
    }

    public Boolean existsById(Long id) {
        return rideRepository.existsById(id);
    }

    public Ride getById(Long id) {
        return rideRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ride with id = '" + id + "' doesn't exists!"));
    }

    public List<Ride> getRidesAsDriverByUser(Long id) {
        return rideRepository.findRidesByUser(id);
    }

    public List<Ride> getRidesAsPassengerByUser(Long id) {
        List<Ride> allRides = rideRepository.findAll();
        List<Ride> rides = new ArrayList<>();

        for (Ride r : allRides) {
            for (User u : r.getPassengers()) {
                if (u.getId().equals(id)) {
                    rides.add(r);
                    break;
                }
            }
        }

        return rides;
    }

    public List<Ride> getRideRequestsByUserAsPassenger(Long id) {
        List<Ride> allRides = rideRepository.findAll();
        List<Ride> rides = new ArrayList<>();

        for (Ride r : allRides) {
            for (User u : r.getRequests()) {
                if (u.getId().equals(id)) {
                    rides.add(r);
                    break;
                }
            }
        }

        return rides;
    }

    public List<Ride> getTodayRides() {
        LocalDateTime now = LocalDateTime.now();
        LocalTime localTime = LocalTime.MIDNIGHT;
        LocalDate localDate = LocalDate.now();
        LocalDateTime midnight = LocalDateTime.of(localDate, localTime).plusDays(1);

        return rideRepository.findRidesInDateRange(now, midnight);
    }

    public List<Ride> getRidesInPriceRange(String currency, double from, double to) {
        return rideRepository.findRidesInPriceRange(currency, from, to);
    }

    public List<Ride> getRidesFromStartingPointToDestination(String startingPointCity, String destinationCity) {
        return rideRepository.findRidesFromStartingPointToDestination(startingPointCity, destinationCity);
    }

    public List<Ride> getRidesFromStartingPointToDestinationInDateRange(String startingPointCity, String destinationCity, String startTime, String endTime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime start = LocalDateTime.parse(startTime, dateTimeFormatter);
        LocalDateTime end = LocalDateTime.parse(endTime, dateTimeFormatter);

        return rideRepository.findRidesFromStartingPointToDestinationInDateRange(startingPointCity, destinationCity, start, end);
    }

    public Boolean driverHasRideInDateRange(String email, LocalDateTime start, LocalDateTime end) {
        User driver = userService.findUserByEmail(email);
        List<Ride> driversRides = rideRepository.findRidesByUser(driver.getId());

        for (Ride ride : driversRides) {
            if ((ride.getDepartureTime().compareTo(start) > 0 && ride.getDepartureTime().compareTo(end) <= 0)
            || (ride.getArrivalTime().compareTo(start) > 0 && ride.getArrivalTime().compareTo(end) < 0)
            || (ride.getDepartureTime().compareTo(start) <= 0 && ride.getArrivalTime().compareTo(end) >= 0)) {
                return true;
            }
        }

        return false;
    }

    public Boolean rideHasFreeSeats(Long rideId) {
        if (existsById(rideId)) {
            Ride ride = getById(rideId);
            if (ride.getPassengers() != null) {
                return ride.getPassengers().size() < ride.getMaxPassengers();
            }
        }

        return true;
    }

    public void checkRide(Ride ride, String loggedUserEmail) {
        if (ride.getDriver() == null) {
            throw new IllegalArgumentException("Driver can't be null!");
        } else if (ride.getDriver().getEmail() == null) {
            throw new IllegalArgumentException("Driver email can't be null!");
        } else if (!ride.getDriver().getEmail().equals(loggedUserEmail)) {
            throw new IllegalArgumentException("User with email '" + ride.getDriver().getEmail() + "' is not logged in!");
        }  else if (!userService.confirmedUserExists(ride.getDriver().getEmail())) {
            throw new IllegalArgumentException("Driver with email '" + ride.getDriver().getEmail() + "' doesn't exist!");
        } else if (ride.getDepartureTime() == null) {
            throw new IllegalArgumentException("Departure time can't be null!");
        } else if (ride.getArrivalTime() == null) {
            throw new IllegalArgumentException("Arrival time can't be null!");
        } else if (ride.getDepartureTime().compareTo(LocalDateTime.now()) < 0) {
            throw new IllegalArgumentException("Departure time of the ride must be after current time!");
        } else if (ride.getArrivalTime().compareTo(LocalDateTime.now()) < 0) {
            throw new IllegalArgumentException("Arrival time of the ride must be after current time!");
        } else if (ride.getDepartureTime().compareTo(ride.getArrivalTime()) >= 0) {
            throw new IllegalArgumentException("Arrival time of the ride must be after departure time!");
        } else if (driverHasRideInDateRange(ride.getDriver().getEmail(), ride.getDepartureTime(), ride.getArrivalTime())) {
            throw new IllegalArgumentException("Driver already has a scheduled ride in that date range!!");
        } else if (ride.getStartingPoint() == null) {
            throw new IllegalArgumentException("Starting point can't be empty or null!");
        } else if (ride.getStartingPoint().getCountry() == null || ride.getStartingPoint().getCountry().equals("")) {
            throw new IllegalArgumentException("Starting point country can't be empty or null!");
        } else if (ride.getStartingPoint().getCity() == null || ride.getStartingPoint().getCity().equals("")) {
            throw new IllegalArgumentException("Starting point city can't be empty or null!");
        } else if (ride.getStartingPoint().getAddress() == null || ride.getStartingPoint().getAddress().equals("")) {
            throw new IllegalArgumentException("Starting point address can't be empty or null!");
        } else if (ride.getDestination() == null) {
            throw new IllegalArgumentException("Destination can't be empty or null!");
        } else if (ride.getDestination().getCountry() == null || ride.getDestination().getCountry().equals("")) {
            throw new IllegalArgumentException("Destination country can't be empty or null!");
        } else if (ride.getDestination().getCity() == null || ride.getDestination().getCity().equals("")) {
            throw new IllegalArgumentException("Destination city can't be empty or null!");
        } else if (ride.getDestination().getAddress() == null || ride.getDestination().getAddress().equals("")) {
            throw new IllegalArgumentException("Destination address can't be empty or null!");
        } else if (ride.getStartingPoint().isSameLocation(ride.getDestination())) {
            throw new IllegalArgumentException("Starting point and destination must be different locations!");
        } else if (ride.getMaxPassengers() < 0) {
            throw new IllegalArgumentException("Maximum number of passengers must be greater or equal to 0!");
        } else if (ride.getPrice() == null) {
            throw new IllegalArgumentException("Price can't be null!");
        } else if (ride.getPrice().getWorth() < 0) {
            throw new IllegalArgumentException("Price worth must be greater or equal to 0!");
        } else if (ride.getPrice().getCurrency() == null || ride.getPrice().getCurrency().equals("")) {
            throw new IllegalArgumentException("Currency should not be empty or null!");
        }
    }

    public Ride createRide(Ride ride) {
        ride.setId(null);
        User driver = userService.findUserByEmail(ride.getDriver().getEmail());
        ride.setDriver(driver);
        ride.setPassengers(new ArrayList<>());
        ride.setRequests(new ArrayList<>());

        return rideRepository.save(ride);
    }

    public Boolean deleteRide(Long id) {
        rideRepository.deleteById(id);

        return true;
    }

    public void checkRideForDelete(Long rideId) {
        if (existsById(rideId)) {
            Ride ride = getById(rideId);

            List<User> passengers = ride.getPassengers();
            if (passengers != null && passengers.size() > 0) {
                ride.setPassengers(new ArrayList<>());
            }

            List<User> rideRequests = ride.getRequests();
            if (rideRequests != null && rideRequests.size() > 0) {
                ride.setRequests(new ArrayList<>());
            }

            rideRepository.save(ride);
        } else {
            throw new IllegalArgumentException("Ride with id = '" + rideId + "' doesn't exist!");
        }
    }

    public void checkRideRequest(RideRequestDTO rideRequestDTO) {
        if (rideRequestDTO == null) {
            throw new IllegalArgumentException("Ride can't be null!");
        } else if (rideRequestDTO.getRideId() == null) {
            throw new IllegalArgumentException("Ride id can't be null!");
        } else if (!existsById(rideRequestDTO.getRideId())) {
            throw new IllegalArgumentException("Ride with id = '" + rideRequestDTO.getRideId() + "' doesn't exist!");
        } else if (userHasRideRequest(getById(rideRequestDTO.getRideId()), rideRequestDTO.getUserEmail())) {
            throw new IllegalArgumentException("User with email '" + rideRequestDTO.getUserEmail() + "' has already requested for ride with id '" + rideRequestDTO.getRideId() + "'!");
        } else if (userIsRideDriver(getById(rideRequestDTO.getRideId()), rideRequestDTO.getUserEmail())) {
            throw new IllegalArgumentException("User with email '" + rideRequestDTO.getUserEmail() + "' is driver for ride with id '" + rideRequestDTO.getRideId() + "'!");
        } else if (userIsRidePassenger(getById(rideRequestDTO.getRideId()), rideRequestDTO.getUserEmail())) {
            throw new IllegalArgumentException("User with email '" + rideRequestDTO.getUserEmail() + "' is already a passenger for ride with id '" + rideRequestDTO.getRideId() + "'");
        }
    }

    public Ride createRideRequest(RideRequestDTO rideRequestDTO) {
        if (existsById(rideRequestDTO.getRideId()) && userService.userExists(rideRequestDTO.getUserEmail())) {
            Ride ride = getById(rideRequestDTO.getRideId());
            User user = userService.findUserByEmail(rideRequestDTO.getUserEmail());
            ride.getRequests().add(user);

            return rideRepository.save(ride);
        } else {
            throw new IllegalArgumentException("Ride with id '" + rideRequestDTO.getRideId() + "' doesn't exist or user with email '" + rideRequestDTO.getUserEmail() + "' doesn't exist!");
        }
    }

    public void cancelRideRequest(RideRequestDTO rideRequestDTO) {
        if (existsById(rideRequestDTO.getRideId()) && userService.userExists(rideRequestDTO.getUserEmail())) {
            Ride ride = getById(rideRequestDTO.getRideId());
            User user = userService.findUserByEmail(rideRequestDTO.getUserEmail());

            if (ride.getRequests().remove(user)) {
                rideRepository.save(ride);
            } else {
                throw new IllegalArgumentException("User with email '" + rideRequestDTO.getUserEmail() + "' is not requested for ride with id ='" + rideRequestDTO.getRideId() + "'!");
            }
        } else {
            throw new IllegalArgumentException("Ride with id '" + rideRequestDTO.getRideId() + "' doesn't exist or user with email '" + rideRequestDTO.getUserEmail() + "' doesn't exist!");
        }
    }

    public void cancelRide(RideRequestDTO rideRequestDTO) {
        if (existsById(rideRequestDTO.getRideId()) && userService.userExists(rideRequestDTO.getUserEmail())) {
            Ride ride = getById(rideRequestDTO.getRideId());
            User user = userService.findUserByEmail(rideRequestDTO.getUserEmail());

            if (ride.getPassengers().remove(user)) {
                rideRepository.save(ride);
            } else {
                throw new IllegalArgumentException("User with email '" + rideRequestDTO.getUserEmail() + "' is not passenger for ride with id ='" + rideRequestDTO.getRideId() + "'!");
            }
        } else {
            throw new IllegalArgumentException("Ride with id '" + rideRequestDTO.getRideId() + "' doesn't exist or user with email '" + rideRequestDTO.getUserEmail() + "' doesn't exist!");
        }
    }

    public void acceptRideRequest(RideRequestDTO rideRequestDTO) {
        if (existsById(rideRequestDTO.getRideId()) && userService.userExists(rideRequestDTO.getUserEmail())) {
            Ride ride = rideRepository.getById(rideRequestDTO.getRideId());
            User user = userService.findUserByEmail(rideRequestDTO.getUserEmail());

            if (!userHasRideRequest(ride, user.getEmail())) {
                throw new IllegalArgumentException("User with email '" + rideRequestDTO.getUserEmail() + "' is not requested for ride with id '" + rideRequestDTO.getRideId() + "'");
            } else if (userIsRidePassenger(ride, user.getEmail())) {
                throw new IllegalArgumentException("User with email '" + rideRequestDTO.getUserEmail() + "' is already a passenger for ride with id '" + rideRequestDTO.getRideId() + "'");
            } else if (userIsRideDriver(ride, user.getEmail())) {
                throw new IllegalArgumentException("User with email '" + rideRequestDTO.getUserEmail() + "' is driver for ride with id '" + rideRequestDTO.getRideId() + "'!");
            } else if (!rideHasFreeSeats(ride.getId())) {
                throw new IllegalArgumentException("Ride with id '" + rideRequestDTO.getRideId() + "' is full! There are no more free seats!");
            }

            ride.getRequests().remove(user);
            ride.getPassengers().add(user);
            rideRepository.save(ride);
        } else {
            throw new IllegalArgumentException("Ride with id '" + rideRequestDTO.getRideId() + "' doesn't exist or user with email '" + rideRequestDTO.getUserEmail() + "' doesn't exist!");
        }
    }

    public void declineRideRequest(RideRequestDTO rideRequestDTO) {
        if (existsById(rideRequestDTO.getRideId()) && userService.userExists(rideRequestDTO.getUserEmail())) {
            Ride ride = rideRepository.getById(rideRequestDTO.getRideId());
            User user = userService.findUserByEmail(rideRequestDTO.getUserEmail());

            if (!userHasRideRequest(ride, user.getEmail())) {
                throw new IllegalArgumentException("User with email '" + rideRequestDTO.getUserEmail() + "' is not requested for ride with id '" + rideRequestDTO.getRideId() + "'");
            } else if (userIsRidePassenger(ride, user.getEmail())) {
                throw new IllegalArgumentException("User with email '" + rideRequestDTO.getUserEmail() + "' is already a passenger for ride with id '" + rideRequestDTO.getRideId() + "'");
            } else if (userIsRideDriver(ride, user.getEmail())) {
                throw new IllegalArgumentException("User with email '" + rideRequestDTO.getUserEmail() + "' is driver for ride with id '" + rideRequestDTO.getRideId() + "'!");
            }

            ride.getRequests().remove(user);
            rideRepository.save(ride);
        } else {
            throw new IllegalArgumentException("Ride with id '" + rideRequestDTO.getRideId() + "' doesn't exist or user with email '" + rideRequestDTO.getUserEmail() + "' doesn't exist!");
        }
    }

    public Boolean userHasRideRequest(Ride rideInDatabase, String userEmail) {
        for (User request : rideInDatabase.getRequests()) {
            if (request.getEmail().equals(userEmail)) {
                return true;
            }
        }

        return false;
    }

    public Boolean userIsRideDriver(Ride rideInDatabase, String userEmail) {
        return rideInDatabase.getDriver().getEmail().equals(userEmail);
    }

    public Boolean userIsRidePassenger(Ride rideInDatabase, String userEmail) {
        for (User request : rideInDatabase.getPassengers()) {
            if (request.getEmail().equals(userEmail)) {
                return true;
            }
        }

        return false;
    }
}
