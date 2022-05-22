package com.example.DriveWithMe.repository;

import com.example.DriveWithMe.model.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RideRepository extends JpaRepository<Ride, Long> {
    @Query("Select r from Ride r where r.driver.id = ?1")
    List<Ride> findRidesByUser(Long id);

    @Query("Select r from Ride r where r.departureTime >= ?1 and r.departureTime <= ?2")
    List<Ride> findRidesInDateRange(LocalDateTime start, LocalDateTime end);

    @Query("Select r from Ride r where r.price.currency = ?1 and r.price.worth >= ?2 and r.price.worth <= ?3")
    List<Ride> findRidesInPriceRange(String currency, double from, double to);

    @Query("Select r from Ride r where r.startingPoint.city = ?1 and r.destination.city = ?2")
    List<Ride> findRidesFromStartingPointToDestination(String startingPointCity, String destinationCity);

    @Query("Select r from Ride r where r.startingPoint.city = ?1 and r.destination.city = ?2 and r.departureTime >= ?3 and r.departureTime <= ?4")
    List<Ride> findRidesFromStartingPointToDestinationInDateRange(String startingPointCity, String destinationCity, LocalDateTime start, LocalDateTime end);
}
