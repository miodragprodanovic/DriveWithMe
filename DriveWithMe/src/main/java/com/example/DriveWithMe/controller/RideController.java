package com.example.DriveWithMe.controller;

import com.example.DriveWithMe.dto.RideRequestDTO;
import com.example.DriveWithMe.model.Ride;
import com.example.DriveWithMe.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/rides/")
@CrossOrigin(origins = "http://localhost:4200")
public class RideController {
    @Autowired
    private final RideService rideService;

    public RideController(RideService rideService) {
        this.rideService = rideService;
    }

    @GetMapping("/allRides")
    public ResponseEntity<List<Ride>> getRides() {
        return new ResponseEntity<>(rideService.getRides(), HttpStatus.OK);
    }

    @GetMapping("/rideById/{rideId}")
    public ResponseEntity<Ride> getRideById(@PathVariable("rideId") Long rideId) {
        return new ResponseEntity<>(rideService.getById(rideId), HttpStatus.OK);
    }

    @GetMapping("/ridesByUserAsDriver")
    public ResponseEntity<List<Ride>> getRidesAsDriverByUser(@RequestParam Long id) {
        return new ResponseEntity<>(rideService.getRidesAsDriverByUser(id), HttpStatus.OK);
    }

    @GetMapping("/ridesByUserAsPassenger")
    public ResponseEntity<List<Ride>> getRidesAsPassengerByUser(@RequestParam Long id) {
        return new ResponseEntity<>(rideService.getRidesAsPassengerByUser(id), HttpStatus.OK);
    }

    @GetMapping("/rideRequestsByUserAsPassenger")
    public ResponseEntity<List<Ride>> getRideRequestsByUserAsPassenger(@RequestParam Long id) {
        return new ResponseEntity<>(rideService.getRideRequestsByUserAsPassenger(id), HttpStatus.OK);
    }

    @GetMapping("/todayRides")
    public ResponseEntity<List<Ride>> getTodayRides() {
        return new ResponseEntity<>(rideService.getTodayRides(), HttpStatus.OK);
    }

    @GetMapping("/ridesInPriceRange")
    public ResponseEntity<List<Ride>> getRidesInPriceRange(@RequestParam String currency, @RequestParam double from, @RequestParam double to) {
        return new ResponseEntity<>(rideService.getRidesInPriceRange(currency, from, to), HttpStatus.OK);
    }

    @GetMapping("/ridesFromStartingPointToDestination")
    public ResponseEntity<List<Ride>> getRidesFromStartingPointToDestination(@RequestParam String startingPointCity, @RequestParam String destinationCity) {
        return new ResponseEntity<>(rideService.getRidesFromStartingPointToDestination(startingPointCity, destinationCity), HttpStatus.OK);
    }

    @GetMapping("/ridesFromStartingPointToDestinationInDateRange")
    public ResponseEntity<List<Ride>> getRidesFromStartingPointToDestinationInDateRange(@RequestParam String startingPointCity, @RequestParam String destinationCity, @RequestParam String startTime, @RequestParam String endTime) {
        return new ResponseEntity<>(rideService.getRidesFromStartingPointToDestinationInDateRange(startingPointCity, destinationCity, startTime, endTime), HttpStatus.OK);
    }

    @GetMapping("/rideHasFreeSeats/{rideId}")
    public ResponseEntity<Boolean> rideHasFreeSeats(@PathVariable("rideId") Long rideId) {
        return new ResponseEntity<>(rideService.rideHasFreeSeats(rideId), HttpStatus.OK);
    }

    @PostMapping("/createRide")
    public ResponseEntity<Ride> createRide(@RequestBody Ride ride, HttpServletRequest request) {
        if (request.getSession().getAttribute("email") != null) {
            String loggedUserEmail = request.getSession().getAttribute("email").toString();
            rideService.checkRide(ride, loggedUserEmail);
            return new ResponseEntity<>(rideService.createRide(ride), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("/deleteRide/{rideId}")
    public ResponseEntity<Boolean> deleteRide(@PathVariable("rideId") Long rideId, HttpServletRequest request) {
        if (request.getSession().getAttribute("email") != null) {
            String loggedUserEmail = request.getSession().getAttribute("email").toString();
            if (rideService.existsById(rideId)) {
                Ride rideInDatabase = rideService.getById(rideId);
                if (rideInDatabase.getDriver().getEmail().equals(loggedUserEmail)) {
                    rideService.checkRideForDelete(rideId);
                    return new ResponseEntity<>(rideService.deleteRide(rideInDatabase.getId()), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
                }
            } else {
                return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/createRideRequest")
    public ResponseEntity<Ride> createRideRequest(@RequestBody RideRequestDTO rideRequestDTO, HttpServletRequest request) {
        if (rideRequestDTO != null && rideRequestDTO.getRideId() != null && rideRequestDTO.getUserEmail() != null) {
            if (request.getSession().getAttribute("email") != null) {
                String loggedUserEmail = request.getSession().getAttribute("email").toString();
                String requestEmail = rideRequestDTO.getUserEmail();
                if (requestEmail.equals(loggedUserEmail)) {
                    rideService.checkRideRequest(rideRequestDTO);
                    return new ResponseEntity<>(rideService.createRideRequest(rideRequestDTO), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
                }
            } else {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/cancelRideRequest")
    public ResponseEntity<Boolean> cancelRideRequest(@RequestBody RideRequestDTO rideRequestDTO, HttpServletRequest request) {
        if (rideRequestDTO != null && rideRequestDTO.getRideId() != null && rideRequestDTO.getUserEmail() != null) {
            if (request.getSession().getAttribute("email") != null) {
                String loggedUserEmail = request.getSession().getAttribute("email").toString();
                String requestEmail = rideRequestDTO.getUserEmail();
                if (requestEmail.equals(loggedUserEmail)) {
                    if (rideService.existsById(rideRequestDTO.getRideId())) {
                        Ride rideInDatabase = rideService.getById(rideRequestDTO.getRideId());
                        if (rideService.userHasRideRequest(rideInDatabase, requestEmail)) {
                            rideService.cancelRideRequest(rideRequestDTO);
                            return new ResponseEntity<>(true, HttpStatus.OK);
                        } else {
                            return new ResponseEntity<>(false, HttpStatus.NOT_ACCEPTABLE);
                        }
                    } else {
                        return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
                    }
                } else {
                    return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
                }
            } else {
                return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/cancelRide")
    public ResponseEntity<Boolean> cancelRide(@RequestBody RideRequestDTO rideRequestDTO, HttpServletRequest request) {
        if (rideRequestDTO != null && rideRequestDTO.getRideId() != null && rideRequestDTO.getUserEmail() != null) {
            if (request.getSession().getAttribute("email") != null) {
                String loggedUserEmail = request.getSession().getAttribute("email").toString();
                String requestEmail = rideRequestDTO.getUserEmail();
                if (requestEmail.equals(loggedUserEmail)) {
                    if (rideService.existsById(rideRequestDTO.getRideId())) {
                        Ride rideInDatabase = rideService.getById(rideRequestDTO.getRideId());
                        if (rideService.userIsRidePassenger(rideInDatabase, requestEmail)) {
                            rideService.cancelRide(rideRequestDTO);
                            return new ResponseEntity<>(true, HttpStatus.OK);
                        } else {
                            return new ResponseEntity<>(false, HttpStatus.NOT_ACCEPTABLE);
                        }
                    } else {
                        return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
                    }
                } else {
                    return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
                }
            } else {
                return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/acceptRideRequest")
    public ResponseEntity<Boolean> acceptRideRequest(@RequestBody RideRequestDTO rideRequestDTO, HttpServletRequest request) {
        if (rideRequestDTO != null && rideRequestDTO.getRideId() != null && rideRequestDTO.getUserEmail() != null) {
            if (request.getSession().getAttribute("email") != null) {
                String loggedUserEmail = request.getSession().getAttribute("email").toString();
                if (rideService.existsById(rideRequestDTO.getRideId())) {
                    Ride rideInDatabase = rideService.getById(rideRequestDTO.getRideId());
                    if (rideInDatabase.getDriver().getEmail().equals(loggedUserEmail)) {
                        rideService.acceptRideRequest(rideRequestDTO);
                        return new ResponseEntity<>(true, HttpStatus.OK);
                    } else {
                        return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
                    }
                } else {
                    return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/declineRideRequest")
    public ResponseEntity<Boolean> declineRideRequest(@RequestBody RideRequestDTO rideRequestDTO, HttpServletRequest request) {
        if (rideRequestDTO != null && rideRequestDTO.getRideId() != null && rideRequestDTO.getUserEmail() != null) {
            if (request.getSession().getAttribute("email") != null) {
                String loggedUserEmail = request.getSession().getAttribute("email").toString();
                if (rideService.existsById(rideRequestDTO.getRideId())) {
                    Ride rideInDatabase = rideService.getById(rideRequestDTO.getRideId());
                    if (rideInDatabase.getDriver().getEmail().equals(loggedUserEmail)) {
                        rideService.declineRideRequest(rideRequestDTO);
                        return new ResponseEntity<>(true, HttpStatus.OK);
                    } else {
                        return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
                    }
                } else {
                    return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

    }
}
