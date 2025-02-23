package com.task.Application.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.task.Application.Entity.Manager;
import com.task.Application.Entity.User;
import com.task.Application.Repo.ManagerRepository;
import com.task.Application.Repo.UserRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ManagerRepository managerRepository;
    

    public User createUser(User user) {
        user.setPanNum(user.getPanNum().toUpperCase());

        if (user.getManagerId() != null) {
            try {
                UUID managerUuid = UUID.fromString(user.getManagerId());
                boolean isValidManager = managerRepository.existsByManagerIdAndIsActiveTrue(managerUuid);

                if (!isValidManager) {
                    throw new IllegalArgumentException("Invalid or inactive manager_id: " + user.getManagerId());
                }

                user.setManagerId(managerUuid.toString()); 
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid manager_id format: " + user.getManagerId());
            }
        }

        return userRepository.save(user);
    }

    public List<User> getUsers(UUID userId, String mobNum, UUID managerId) {
        if (userId != null) {
            return userRepository.findById(userId)
                    .map(Collections::singletonList)
                    .orElse(Collections.emptyList());
        }
        if (mobNum != null) {
            return userRepository.findByMobNum(mobNum)
                    .map(Collections::singletonList)
                    .orElse(Collections.emptyList());
        }
        if (managerId != null) {
            return userRepository.findByManagerId(managerId);
        }
        return userRepository.findAll();
    }

    
    public List<Manager> getAllManagers() {
        List<Manager> managers = managerRepository.findAll();
        System.out.println("Managers in DB: " + managers);
        return managers;
    }


    @Transactional
    public void deleteUser(UUID userId, String mobNum) {
        if (userId != null) {
            if (!userRepository.existsById(userId)) {
                throw new IllegalArgumentException("User ID not found: " + userId);
            }
            userRepository.deleteById(userId);
        } else if (mobNum != null) {
            Optional<User> user = userRepository.findByMobNum(mobNum);
            if (user.isPresent()) {
                userRepository.delete(user.get());
            } else {
                throw new IllegalArgumentException("User with mobile number not found: " + mobNum);
            }
        } else {
            throw new IllegalArgumentException("Provide either user_id or mob_num");
        }
    }

    @Transactional
    public void updateUser(UUID userId, User updatedData) {
        Optional<User> userOpt = userRepository.findById(userId);
        
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("User ID not found: " + userId);
        }
        
        User existingUser = userOpt.get();
        if (updatedData.getFullName() != null) existingUser.setFullName(updatedData.getFullName());
        if (updatedData.getMobNum() != null) existingUser.setMobNum(updatedData.getMobNum());
        if (updatedData.getPanNum() != null) existingUser.setPanNum(updatedData.getPanNum().toUpperCase());
        if (updatedData.getManagerId() != null) existingUser.setManagerId(updatedData.getManagerId());
        
        existingUser.setUpdatedAt(LocalDateTime.now());
        userRepository.save(existingUser);
    }
}
