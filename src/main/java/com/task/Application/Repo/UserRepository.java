package com.task.Application.Repo;


import org.springframework.data.jpa.repository.JpaRepository;

import com.task.Application.Entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByMobNum(String mobNum);
    List<User> findByManagerId(UUID managerId);
}
