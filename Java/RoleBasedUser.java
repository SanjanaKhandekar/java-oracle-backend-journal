// Day 18: Mapping dynamic user roles (ADMIN, USER, MANAGER) using JPA Enums
// Topic: Database Modeling & Role-Based Access Control (RBAC)

import jakarta.persistence.*;

public class RoleBasedUser {

    // Define strict system roles using a clean Java Enum
    public enum UserRole {
        ADMIN, USER, MANAGER
    }

    @Entity
    @Table(name = "SEC_USERS")
    public class User {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(unique = true, nullable = false, length = 50)
        private String username;

        @Column(nullable = false)
        private String password; // Stored securely as an encrypted string

        @Enumerated(EnumType.STRING) // Saves the actual text name ("ADMIN") into the Oracle DB column
        @Column(name = "user_role", nullable = false, length = 20)
        private UserRole role;

        // Default Constructor
        public User() {}

        // Getters and Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        public UserRole getRole() { return role; }
        public void setRole(UserRole role) { this.role = role; }
    }
}

// Benefit: Using EnumType.STRING keeps your database readable. If you use the default 
// ordinal mapping, it saves numbers (0, 1, 2) which makes database debugging difficult. 
// String enums make it perfectly clear to any DB administrator what role an account holds.
