package com.example.DriveWithMe.controller;

import com.example.DriveWithMe.adapter.UserAdapter;
import com.example.DriveWithMe.dto.ChangePasswordDTO;
import com.example.DriveWithMe.dto.UserDTO;
import com.example.DriveWithMe.dto.UserLoginDTO;
import com.example.DriveWithMe.dto.UserRegisterDTO;
import com.example.DriveWithMe.model.User;
import com.example.DriveWithMe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/users/")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    @Autowired
    private final UserService userService;
    @Autowired
    private final UserAdapter userAdapter;

    public UserController(UserService userService, UserAdapter userAdapter) {
        this.userService = userService;
        this.userAdapter = userAdapter;
    }

    @GetMapping("/allUsers")
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<User> allUsers = this.userService.getUsers();
        List<UserDTO> allUserDTOs = this.userAdapter.UsersToUserDTOs(allUsers);

        return new ResponseEntity<>(allUserDTOs, HttpStatus.OK);
    }

    @GetMapping("/userByEmail")
    public ResponseEntity<User> findUserByEmail(@RequestParam String email, HttpServletRequest request) {
        if (request.getSession().getAttribute("email") != null) {
            if (request.getSession().getAttribute("email").equals(email)) {
                return new ResponseEntity<>(userService.findUserByEmail(email, true), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(userService.findUserByEmail(email, false), HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(userService.findUserByEmail(email, false), HttpStatus.OK);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserRegisterDTO userRegisterDTO) {
        User user = this.userAdapter.UserRegisterDTOToUser(userRegisterDTO);
        if (userService.checkRegistrationUser(user)) {
            if (userService.userExists(user.getEmail())) {
                if (userService.findUserByEmail(user.getEmail()).getConfirmed()) {
                    return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
                } else {
                    User newUser = userService.register(user);
                    return new ResponseEntity<>(this.userAdapter.UserToUserDTO(newUser), HttpStatus.OK);
                }
            } else {
                User newUser = userService.register(user);
                return new ResponseEntity<>(this.userAdapter.UserToUserDTO(newUser), HttpStatus.CREATED);
            }
        } else {
            return new ResponseEntity<>(null, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @GetMapping("/activate")
    public ResponseEntity<UserDTO> activateUser(@RequestParam("token") String token) {
        User user = userService.activate(token);
        return new ResponseEntity<>(this.userAdapter.UserToUserDTO(user), HttpStatus.OK);
    }

    @PostMapping("/canLogin")
    public ResponseEntity<Boolean> canLogin(@RequestBody UserLoginDTO userLoginDTO) {
        User user = this.userAdapter.UserLoginDTOToUser(userLoginDTO);
        return new ResponseEntity<>(userService.canLogin(user), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Boolean> login(@RequestBody UserLoginDTO userLoginDTO, HttpServletRequest request) {
        User user = this.userAdapter.UserLoginDTOToUser(userLoginDTO);
        if (userService.canLogin(user)) {
            if (request.getSession().getAttribute("email") == null) {
                request.getSession(true).setAttribute("email", user.getEmail());
            }
            else {
                request.getSession(false).setAttribute("email", user.getEmail());
            }

            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/loggedUser")
    public ResponseEntity<UserDTO> findLoggedUser(HttpServletRequest request) {
        if (request.getSession().getAttribute("email") == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
            String email = (String) request.getSession().getAttribute("email");
            User user = userService.findUserByEmail(email);
            return new ResponseEntity<>(this.userAdapter.UserToUserDTO(user), HttpStatus.OK);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Boolean> logout(@RequestBody UserDTO userDTO, HttpServletRequest request) {
        String email = request.getSession().getAttribute("email").toString();
        if (userDTO.getEmail() != null && userDTO.getEmail().equals(email)) {
            request.getSession().setAttribute("email", null);
            request.getSession().invalidate();
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/changeInfo")
    public ResponseEntity<UserDTO> changeInfo(@RequestBody UserDTO userDTO, HttpServletRequest request) {
        if (request.getSession().getAttribute("email") != null) {
            if (request.getSession().getAttribute("email").equals(userDTO.getEmail())) {
                User user = this.userAdapter.UserDTOToUser(userDTO);
                UserDTO newUserDTO = this.userAdapter.UserToUserDTO(userService.changeInfo(user));
                return new ResponseEntity<>(newUserDTO, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/changePassword")
    public ResponseEntity<UserDTO> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO, HttpServletRequest request) {
        if (request.getSession().getAttribute("email") != null) {
            if (request.getSession().getAttribute("email").equals(changePasswordDTO.getEmail())) {
                UserDTO newUserDTO = this.userAdapter.UserToUserDTO(userService.changePassword(changePasswordDTO));

                return new ResponseEntity<>(newUserDTO, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }

}
