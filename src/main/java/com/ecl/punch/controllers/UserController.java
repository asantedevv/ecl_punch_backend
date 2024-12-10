package com.ecl.punch.controllers;


import com.ecl.punch.dtos.UserListResponse;
import com.ecl.punch.dtos.UserResponse;
import com.ecl.punch.models.User;
import com.ecl.punch.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PostMapping("/find-by-id/{id}")
    public UserResponse findById(@PathVariable Integer id) {
        return userService.findUserById(id);
    }

    @PostMapping("/hello")
    public String findById() {
        return "hello";
    }



    @PostMapping("/find-all-users")
    public UserListResponse findAllUsers() {
        return userService.findAllUsers();
    }

    @PostMapping("/update-by-id/{id}")
    public UserResponse updateUserById(@PathVariable Integer id, @RequestBody User user) {
        return userService.updateUserById(id, user);
    }

    @PostMapping("/delete-user/{id}")
    public UserResponse deleteUserById(@PathVariable Integer id) {
        return userService.deleteUserById(id);
    }

}
