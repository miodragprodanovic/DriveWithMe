package com.example.DriveWithMe.controller;

import com.example.DriveWithMe.adapter.RideAdapter;
import com.example.DriveWithMe.dto.RideDTO;
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
    @Autowired
    private final RideAdapter rideAdapter;

    public RideController(RideService rideService, RideAdapter rideAdapter) {
        this.rideService = rideService;
        this.rideAdapter = rideAdapter;
    }

    @GetMapping("/allRides")
    public ResponseEntity<List<RideDTO>> getRides() {
        List<Ride> allRides = rideService.getRides();
        List<RideDTO> allRideDTOs = this.rideAdapter.RidesToRideDTOs(allRides);

        return new ResponseEntity<>(allRideDTOs, HttpStatus.OK);
    }

    @GetMapping("/rideById/{rideId}")
    public ResponseEntity<RideDTO> getRideById(@PathVariable("rideId") Long rideId) {
        RideDTO rideDTO = this.rideAdapter.RideToRideDTO(rideService.getById(rideId));

        return new ResponseEntity<>(rideDTO, HttpStatus.OK);
    }

    @GetMapping("/ridesByUserAsDriver")
    public ResponseEntity<List<RideDTO>> getRidesAsDriverByUser(@RequestParam Long id) {
        List<RideDTO> rideDTOs = this.rideAdapter.RidesToRideDTOs(rideService.getRidesAsDriverByUser(id));

        return new ResponseEntity<>(rideDTOs, HttpStatus.OK);
    }

    @GetMapping("/ridesByUserAsPassenger")
    public ResponseEntity<List<RideDTO>> getRidesAsPassengerByUser(@RequestParam Long id) {
        List<RideDTO> rideDTOs = this.rideAdapter.RidesToRideDTOs(rideService.getRidesAsPassengerByUser(id));

        return new ResponseEntity<>(rideDTOs, HttpStatus.OK);
    }

    @GetMapping("/rideRequestsByUserAsPassenger")
    public ResponseEntity<List<RideDTO>> getRideRequestsByUserAsPassenger(@RequestParam Long id) {
        List<RideDTO> rideDTOs = this.rideAdapter.RidesToRideDTOs(rideService.getRideRequestsByUserAsPassenger(id));

        return new ResponseEntity<>(rideDTOs, HttpStatus.OK);
    }

    @GetMapping("/todayRides")
    public ResponseEntity<List<RideDTO>> getTodayRides() {
        List<RideDTO> rideDTOs = this.rideAdapter.RidesToRideDTOs(rideService.getTodayRides());

        return new ResponseEntity<>(rideDTOs, HttpStatus.OK);
    }

    @GetMapping("/ridesInPriceRange")
    public ResponseEntity<List<RideDTO>> getRidesInPriceRange(@RequestParam String currency, @RequestParam double from, @RequestParam double to) {
        List<RideDTO> rideDTOs = this.rideAdapter.RidesToRideDTOs(rideService.getRidesInPriceRange(currency, from, to));

        return new ResponseEntity<>(rideDTOs, HttpStatus.OK);
    }

    @GetMapping("/ridesFromStartingPointToDestination")
    public ResponseEntity<List<RideDTO>> getRidesFromStartingPointToDestination(@RequestParam String startingPointCity, @RequestParam String destinationCity) {
        List<RideDTO> rideDTOs = this.rideAdapter.RidesToRideDTOs(rideService.getRidesFromStartingPointToDestination(startingPointCity, destinationCity));

        return new ResponseEntity<>(rideDTOs, HttpStatus.OK);
    }

    @GetMapping("/ridesFromStartingPointToDestinationInDateRange")
    public ResponseEntity<List<RideDTO>> getRidesFromStartingPointToDestinationInDateRange(@RequestParam String startingPointCity, @RequestParam String destinationCity, @RequestParam String startTime, @RequestParam String endTime) {
        List<RideDTO> rideDTOs = this.rideAdapter.RidesToRideDTOs(rideService.getRidesFromStartingPointToDestinationInDateRange(startingPointCity, destinationCity, startTime, endTime));

        return new ResponseEntity<>(rideDTOs, HttpStatus.OK);
    }

    @GetMapping("/rideHasFreeSeats/{rideId}")
    public ResponseEntity<Boolean> rideHasFreeSeats(@PathVariable("rideId") Long rideId) {
        return new ResponseEntity<>(rideService.rideHasFreeSeats(rideId), HttpStatus.OK);
    }

    @PostMapping("/createRide")
    public ResponseEntity<RideDTO> createRide(@RequestBody RideDTO rideDTO, HttpServletRequest request) {
        if (request.getSession().getAttribute("email") != null) {
            String loggedUserEmail = request.getSession().getAttribute("email").toString();
            Ride ride = this.rideAdapter.RideDTOToRide(rideDTO);
            rideService.checkRide(ride, loggedUserEmail);

            return new ResponseEntity<>(this.rideAdapter.RideToRideDTO(rideService.createRide(ride)), HttpStatus.CREATED);
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
    public ResponseEntity<RideDTO> createRideRequest(@RequestBody RideRequestDTO rideRequestDTO, HttpServletRequest request) {
        if (rideRequestDTO != null && rideRequestDTO.getRideId() != null && rideRequestDTO.getUserEmail() != null) {
            if (request.getSession().getAttribute("email") != null) {
                String loggedUserEmail = request.getSession().getAttribute("email").toString();
                String requestEmail = rideRequestDTO.getUserEmail();
                if (requestEmail.equals(loggedUserEmail)) {
                    rideService.checkRideRequest(rideRequestDTO);
                    RideDTO rideDTO = this.rideAdapter.RideToRideDTO(rideService.createRideRequest(rideRequestDTO));
                    return new ResponseEntity<>(rideDTO, HttpStatus.OK);
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
