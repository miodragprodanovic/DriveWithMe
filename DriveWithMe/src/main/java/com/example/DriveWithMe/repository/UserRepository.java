package com.example.DriveWithMe.repository;

import com.example.DriveWithMe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("Select u from User u where u.email = ?1")
    Optional<User> findUserByEmail(String email);

    @Query("Select u from User u where u.email = ?1 and u.password = ?2 and u.confirmed = true")
    Optional<User> findConfirmedUserByEmailAndPassword(String email, String password);
}
