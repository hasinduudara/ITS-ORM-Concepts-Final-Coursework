package lk.ijse.gdse.mentalhealth.entity;

import jakarta.persistence.*;
import lk.ijse.gdse.mentalhealth.util.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "user")

public class User {
    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password; // Will be stored as a hashed password (BCrypt)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role; // Enum (ADMIN, RECEPTIONIST, THERAPIST)

    public User(String name, String username, String email, String password, Role role) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = (role != null) ? role : Role.RECEPTIONIST;
    }
}
