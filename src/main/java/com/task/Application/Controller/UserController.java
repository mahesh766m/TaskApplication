package com.task.Application.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.task.Application.Entity.User;
import com.task.Application.Service.UserService;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            User newUser = userService.createUser(user);
            return ResponseEntity.ok(newUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(Collections.singletonMap("error", e.getMessage()));
        }
    }
    
    
    

    @PostMapping("/get")
    public ResponseEntity<?> getUsers(@RequestBody Map<String, String> request) {
        try {
            UUID userUUID = request.containsKey("userId") && request.get("userId") != null 
                            ? UUID.fromString(request.get("userId")) : null;
            String mobNum = request.get("mobNum");
            UUID managerUUID = request.containsKey("managerId") && request.get("managerId") != null 
                               ? UUID.fromString(request.get("managerId")) : null;

            List<User> users = userService.getUsers(userUUID, mobNum, managerUUID);
            return ResponseEntity.ok(users);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("error", "Invalid UUID format: " + e.getMessage()));
        }
    }

    
    
    
    @PostMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestBody Map<String, String> request) {
        try {
            UUID userId = request.containsKey("userId") ? UUID.fromString(request.get("userId")) : null;
            String mobNum = request.get("mobNum");

            userService.deleteUser(userId, mobNum);
            return ResponseEntity.ok(Collections.singletonMap("message", "User deleted successfully"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody Map<String, Object> request) {
        try {
            UUID userId = UUID.fromString(request.get("userId").toString());
            User updatedData = new User();
            
            if (request.containsKey("fullName")) updatedData.setFullName(request.get("fullName").toString());
            if (request.containsKey("mobNum")) updatedData.setMobNum(request.get("mobNum").toString());
            if (request.containsKey("panNum")) updatedData.setPanNum(request.get("panNum").toString().toUpperCase());
            if (request.containsKey("managerId")) updatedData.setManagerId(request.get("managerId").toString());

            userService.updateUser(userId, updatedData);
            return ResponseEntity.ok(Collections.singletonMap("message", "User updated successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("error", e.getMessage()));
        }
    }
}
