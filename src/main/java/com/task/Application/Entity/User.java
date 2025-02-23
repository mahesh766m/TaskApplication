package com.task.Application.Entity;


import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;
import java.time.LocalDateTime;



import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private UUID userId;


    @Column(length = 36)
    private String managerId;


    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true, length = 13)
    private String mobNum;

    @Column(nullable = false, unique = true, length = 10)
    private String panNum;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt;
    private boolean isActive = true;
}
