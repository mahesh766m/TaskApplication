package com.task.Application.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.task.Application.Entity.Manager;

import java.util.UUID;

@Repository

public interface ManagerRepository extends JpaRepository<Manager, UUID> {
    @Query("SELECT COUNT(m) > 0 FROM Manager m WHERE m.managerId = :managerId AND m.isActive = true")
    boolean existsByManagerIdAndIsActiveTrue(@Param("managerId") UUID managerId);
}



