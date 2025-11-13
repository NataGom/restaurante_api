package co.edu.umanizales.restaurante_api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * System user with authentication and role management
 * Associated with a Person (Student or Teacher)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SystemUser {
    private long id;
    private String username;
    private String password; // In production, this should be encrypted
    private UserRole role;
    private Person person; // Association with Person
    
    /**
     * Authenticates the user with provided credentials
     */
    public boolean authenticate(String username, String password) {
        return this.username != null && this.username.equals(username) 
            && this.password != null && this.password.equals(password);
    }
    
    /**
     * Checks if user has admin privileges
     */
    public boolean isAdmin() {
        return this.role == UserRole.ADMIN;
    }
    
    /**
     * Checks if user is a teacher
     */
    public boolean isTeacher() {
        return this.role == UserRole.TEACHER;
    }
    
    /**
     * Checks if user is a student
     */
    public boolean isStudent() {
        return this.role == UserRole.STUDENT;
    }
}
