package com.ecl.punch.services;

import com.ecl.punch.dtos.UserListResponse;
import com.ecl.punch.dtos.UserResponse;
import com.ecl.punch.models.User;
import com.ecl.punch.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j

public class UserService {

    private final UserRepository userRepository;

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public UserResponse findUserById(Integer id) {

        UserResponse userResponse = new UserResponse();
        try{
            User user = userRepository.findById(id).orElse(null);

            userResponse.setResponseCode("000");
            userResponse.setResponseMessage("User found successfully");
            userResponse.setData(user);
        } catch (Exception e) {
            userResponse.setResponseCode("111");
            userResponse.setResponseMessage("User not found");
        }
        return userResponse;
    }

    public UserListResponse findAllUsers() {

        UserListResponse userListResponse = new UserListResponse();

        try {
            List<User> users = userRepository.findAll();
            userListResponse.setResponseCode("000");
            userListResponse.setResponseMessage("Found all Users");
            userListResponse.setData(users);

        } catch (Exception e) {
            userListResponse.setResponseCode("111");
            userListResponse.setResponseMessage("Users not found");
        }
        return userListResponse;
    }

    public UserResponse updateUserById(Integer id, User updatedUser) {

        UserResponse userResponse = new UserResponse();

        try {
            User user = userRepository.findById(id).orElse(null);

            user.setEmail(updatedUser.getEmail());
            user.setPassword(updatedUser.getPassword());
            user.setPunchInStatus(updatedUser.getPunchInStatus());

            userRepository.save(user);
            userResponse.setResponseCode("000");
            userResponse.setResponseMessage("User updated successfully");
            userResponse.setData(user);

        } catch (Exception e) {
            userResponse.setResponseCode("111");
            userResponse.setResponseMessage("Could not update user");
        }
        return userResponse;
    }


    public UserResponse deleteUserById(Integer id) {

        UserResponse userResponse = new UserResponse();

        try {
            User user = userRepository.findById(id).orElseThrow();
            user.setDeleteStatus("YES");

            userRepository.save(user);
            userResponse.setResponseCode("000");
            userResponse.setResponseMessage("User deleted successfully");
            userResponse.setData(user);

        } catch (Exception e) {
            userResponse.setResponseCode("111");
            userResponse.setResponseMessage("Could not delete user");
        }
        return userResponse ;
    }

    public UserResponse restoreUserById(Integer id) {
        UserResponse userResponse = new UserResponse();

        try{
            User user = userRepository.findById(id).orElseThrow();

            user.setDeleteStatus("NO");

            userRepository.save(user);
            userResponse.setResponseCode("000");
            userResponse.setResponseMessage("User restored successfully");
            userResponse.setData(user);

        } catch (Exception e) {
            userResponse.setResponseCode("111");
            userResponse.setResponseMessage("Could not restore user");
        }

        return userResponse;
    }

}
