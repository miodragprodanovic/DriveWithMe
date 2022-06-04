package com.example.DriveWithMe.service;

import com.example.DriveWithMe.dto.ChangePasswordDTO;
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

    public Boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User with id = '" + id + "' doesn't exists!"));
    }

    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User with email '" + email + "' not found!"));
    }

    public User findUserByEmail(String email, Boolean userLoggedIn) {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User with email '" + email + "' not found!"));
        if (!userLoggedIn) {
            user.setPassword(null);
        }
        return user;
    }

    public Boolean userExists(String email) {
        return userRepository.findUserByEmail(email).isPresent();
    }

    public Boolean confirmedUserExists(String email) {
        return userRepository.findConfirmedUserByEmail(email).isPresent();
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

    public Boolean checkRegistrationUser(User user) {
        if (user.getEmail() == null || user.getEmail().equals("")) {
            throw new IllegalArgumentException("Email can't be empty or null!");
        } else if (user.getPassword() == null || user.getPassword().equals("")) {
            throw new IllegalArgumentException("Password can't be empty or null!");
        } else if (user.getPassword().length() < 8 || user.getPassword().length() > 30) {
            throw new IllegalArgumentException("Password must have between 8 and 30 characters!");
        } else if (user.getFirstName() == null || user.getFirstName().equals("")) {
            throw new IllegalArgumentException("First name can't be empty or null!");
        } else if (user.getLastName() == null || user.getLastName().equals("")) {
            throw new IllegalArgumentException("Last name can't be empty or null!");
        } else if (user.getMobileNumber() == null || user.getMobileNumber().equals("")) {
            throw new IllegalArgumentException("Mobile number name can't be empty or null!");
        }

        return true;
    }

    public Boolean canLogin(User user) {
        return userRepository.findConfirmedUserByEmailAndPassword(user.getEmail(), user.getPassword()).isPresent();
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

    public Boolean checkChangeInfoUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User can't be null or empty!");
        } else if (user.getEmail() == null || user.getEmail().equals("")) {
            throw new IllegalArgumentException("Email can't be null or empty!");
        } else if (user.getFirstName() == null || user.getFirstName().equals("")) {
            throw new IllegalArgumentException("First name can't be empty or null!");
        } else if (user.getLastName() == null || user.getLastName().equals("")) {
            throw new IllegalArgumentException("Last name can't be empty or null!");
        }

        return true;
    }

    public User changeInfo(User newUser) {
        if (checkChangeInfoUser(newUser)) {
            User user = findUserByEmail(newUser.getEmail());

            if (newUser.getFirstName() != null && !newUser.getFirstName().equals("")) {
                user.setFirstName(newUser.getFirstName());
            }
            if (newUser.getLastName() != null && !newUser.getLastName().equals("")) {
                user.setLastName(newUser.getLastName());
            }
            if (newUser.getMobileNumber() != null) {
                user.setMobileNumber(newUser.getMobileNumber());
            }
            if (newUser.getLocation() != null) {
                if (newUser.getLocation().getCountry() != null) {
                    user.getLocation().setCountry(newUser.getLocation().getCountry());
                }
                if (newUser.getLocation().getCity() != null) {
                    user.getLocation().setCity(newUser.getLocation().getCity());
                }
                if (newUser.getLocation().getAddress() != null) {
                    user.getLocation().setAddress(newUser.getLocation().getAddress());
                }
            }
            if (newUser.getBiography() != null) {
                user.setBiography(newUser.getBiography());
            }
            if (newUser.getCar() != null) {
                user.setCar(newUser.getCar());
            }

            return userRepository.save(user);
        } else {
            throw new IllegalArgumentException("Wrong request!");
        }
    }

    public Boolean checkChangePasswordDTO(ChangePasswordDTO changePasswordDTO) {
        if (changePasswordDTO == null) {
            throw new IllegalArgumentException("ChangePasswordDTO can't be null or empty!");
        } else if (changePasswordDTO.getEmail() == null || changePasswordDTO.getEmail().equals("")) {
            throw new IllegalArgumentException("Email can't be null or empty!");
        } else if (changePasswordDTO.getOldPassword() == null || changePasswordDTO.getOldPassword().equals("")) {
            throw new IllegalArgumentException("Old password can't be null or empty!");
        } else if (changePasswordDTO.getNewPassword() == null || changePasswordDTO.getNewPassword().equals("")) {
            throw new IllegalArgumentException("New password can't be null or empty!");
        } else if (changePasswordDTO.getNewPassword().length() < 8 || changePasswordDTO.getNewPassword().length() > 30) {
            throw new IllegalArgumentException("New password must have between 8 and 30 characters!");
        }

        return true;
    }

    public User changePassword(ChangePasswordDTO changePasswordDTO) {
        if (checkChangePasswordDTO(changePasswordDTO)) {
            User user = findUserByEmail(changePasswordDTO.getEmail());

            if (user.getPassword().equals(changePasswordDTO.getOldPassword())) {
                user.setPassword(changePasswordDTO.getNewPassword());

                return userRepository.save(user);
            } else {
                throw new IllegalArgumentException("Old password in request doesn't match the one from database!");
            }
        } else {
            throw new IllegalArgumentException("Wrong request!");
        }
    }
}
