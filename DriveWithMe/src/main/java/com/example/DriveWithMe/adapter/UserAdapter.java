package com.example.DriveWithMe.adapter;

import com.example.DriveWithMe.dto.UserDTO;
import com.example.DriveWithMe.dto.UserLoginDTO;
import com.example.DriveWithMe.dto.UserRegisterDTO;
import com.example.DriveWithMe.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserAdapter {
    public UserAdapter() {}
    public User UserDTOToUser(UserDTO userDTO) {
        User user = new User();

        user.setId(userDTO.getId());
        user.setEmail(userDTO.getEmail());
        //user.setPassword(userDTO.getPassword());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setLocation(userDTO.getLocation());
        user.setMobileNumber(userDTO.getMobileNumber());
        user.setBiography(userDTO.getBiography());
        user.setCar(userDTO.getCar());
        user.setUserRole(userDTO.getUserRole());
        user.setConfirmed(userDTO.getConfirmed());

        return user;
    }

    public UserDTO UserToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setLocation(user.getLocation());
        userDTO.setMobileNumber(user.getMobileNumber());
        userDTO.setBiography(user.getBiography());
        userDTO.setCar(user.getCar());
        userDTO.setUserRole(user.getUserRole());
        userDTO.setConfirmed(user.getConfirmed());

        return userDTO;
    }

    public List<User> UserDTOsToUsers(List<UserDTO> userDTOs) {
        List<User> users = new ArrayList<>();

        for (UserDTO userDTO : userDTOs) {
            users.add(UserDTOToUser(userDTO));
        }

        return users;
    }

    public List<UserDTO> UsersToUserDTOs(List<User> users) {
        List<UserDTO> userDTOs = new ArrayList<>();

        for (User user : users) {
            userDTOs.add(UserToUserDTO(user));
        }

        return userDTOs;
    }

    public User UserRegisterDTOToUser(UserRegisterDTO userRegisterDTO) {
        User user = new User();

        //user.setId(userRegisterDTO.getId());
        user.setEmail(userRegisterDTO.getEmail());
        user.setPassword(userRegisterDTO.getPassword());
        user.setFirstName(userRegisterDTO.getFirstName());
        user.setLastName(userRegisterDTO.getLastName());
        user.setLocation(userRegisterDTO.getLocation());
        user.setMobileNumber(userRegisterDTO.getMobileNumber());
        //user.setBiography(userRegisterDTO.getBiography());
        //user.setCar(userRegisterDTO.getCar());
        user.setUserRole(userRegisterDTO.getUserRole());
        user.setConfirmed(false);

        return user;
    }

    public UserRegisterDTO UserToUserRegisterDTO(User user) {
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();

        userRegisterDTO.setEmail(user.getEmail());
        userRegisterDTO.setPassword(user.getPassword());
        userRegisterDTO.setFirstName(user.getFirstName());
        userRegisterDTO.setLastName(user.getLastName());
        userRegisterDTO.setLocation(user.getLocation());
        userRegisterDTO.setMobileNumber(user.getMobileNumber());
        userRegisterDTO.setUserRole(user.getUserRole());

        return userRegisterDTO;
    }

    public User UserLoginDTOToUser(UserLoginDTO userLoginDTO) {
        User user = new User();

        //user.setId(userLoginDTO.getId());
        user.setEmail(userLoginDTO.getEmail());
        user.setPassword(userLoginDTO.getPassword());
        //user.setFirstName(userLoginDTO.getFirstName());
        //user.setLastName(userLoginDTO.getLastName());
        //user.setLocation(userLoginDTO.getLocation());
        //user.setMobileNumber(userLoginDTO.getMobileNumber());
        //user.setBiography(userLoginDTO.getBiography());
        //user.setCar(userLoginDTO.getCar());
        //user.setUserRole(userLoginDTO.getUserRole());
        //user.setConfirmed(false);

        return user;
    }

    public UserLoginDTO UserToUserLoginDTO(User user) {
        UserLoginDTO userLoginDTO = new UserLoginDTO();

        userLoginDTO.setEmail(user.getEmail());
        userLoginDTO.setPassword(user.getPassword());

        return userLoginDTO;
    }
}
