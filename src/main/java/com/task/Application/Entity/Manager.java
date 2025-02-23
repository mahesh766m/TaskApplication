package com.task.Application.Entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "managers")
public class Manager {
    @Id
    @GeneratedValue
    
    @Column(columnDefinition = "BINARY(16)")
    private UUID managerId;
    
    @Column(nullable = false)
    private String fullName;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false)
    private boolean isActive = true;
}
