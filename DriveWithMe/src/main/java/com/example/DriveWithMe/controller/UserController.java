package com.example.DriveWithMe.controller;

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

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/allUsers")
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<>(this.userService.getUsers(), HttpStatus.OK);
    }

    @GetMapping("/userByEmail")
    public ResponseEntity<User> findUserByEmail(@RequestParam String email) {
        return new ResponseEntity<>(userService.findUserByEmail(email), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        if (userService.checkRegistrationUser(user)) {
            if (userService.userExists(user.getEmail())) {
                if (userService.findUserByEmail(user.getEmail()).getConfirmed()) {
                    return new ResponseEntity<>((User) null, HttpStatus.NOT_ACCEPTABLE);
                } else {
                    User newUser = userService.register(user);
                    return new ResponseEntity<>(newUser, HttpStatus.OK);
                }
            } else {
                User newUser = userService.register(user);
                return new ResponseEntity<>(newUser, HttpStatus.CREATED);
            }
        } else {
            return new ResponseEntity<>((User) null, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @GetMapping("/activate")
    public ResponseEntity<User> activateUser(@RequestParam("token") String token) {
        User user = userService.activate(token);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/canLogin")
    public ResponseEntity<Boolean> canLogin(@RequestBody User user) {
        return new ResponseEntity<>(userService.canLogin(user), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Boolean> login(@RequestBody User user, HttpServletRequest request) {
        if (userService.canLogin(user)){
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
    public ResponseEntity<User> findLoggedUser(HttpServletRequest request) {
        if (request.getSession().getAttribute("email") == null) {
            return new ResponseEntity<>((User) null, HttpStatus.NOT_FOUND);
        } else {
            String email = (String) request.getSession().getAttribute("email");
            User user = userService.findUserByEmail(email);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Boolean> logout(@RequestBody User user, HttpServletRequest request) {
        String email = request.getSession().getAttribute("email").toString();
        if (user.getEmail() != null && user.getEmail().equals(email)) {
            request.getSession().setAttribute("email", null);
            request.getSession().invalidate();
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
    }

}
