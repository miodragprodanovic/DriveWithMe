package com.example.DriveWithMe.service;

import com.example.DriveWithMe.model.ConfirmationToken;
import com.example.DriveWithMe.model.User;
import com.example.DriveWithMe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final EmailService emailService;
    @Autowired
    private final ConfirmationTokenService tokenService;

    public UserService(UserRepository userRepository, EmailService emailService, ConfirmationTokenService tokenService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.tokenService = tokenService;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User register(User user) {
        if (!userRepository.findUserByEmail(user.getEmail()).isPresent()) {
            user.setConfirmed(false);
            userRepository.save(user);
        }

        ConfirmationToken token = tokenService.saveToken(user);
        emailService.sendEmailForVerification(token);

        return user;
    }

    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User with email '" + email + "' not found!"));
    }

    public boolean userExists(String email) {
        return userRepository.findUserByEmail(email).isPresent();
    }

    public User activate(String token){
        ConfirmationToken confirmationToken = tokenService.getToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Token not found!"));
        User user = userRepository.findUserByEmail(confirmationToken.getUser().getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User with email '" + confirmationToken.getUser().getEmail() + "' not found!"));

        if (confirmationToken.getConfirmed()) {
            throw new IllegalStateException("Account already activated");
        }
        tokenService.setConfirmed(confirmationToken);

        if (user.getConfirmed()) {
            throw new IllegalStateException("Account already activated");
        } else {
            user.setConfirmed(true);
            userRepository.save(user);
        }

        return user;
    }

    public Boolean canLogin(User user) {
        return userRepository.findConfirmedUserByEmailAndPassword(user.getEmail(), user.getPassword()).isPresent();
    }
}
