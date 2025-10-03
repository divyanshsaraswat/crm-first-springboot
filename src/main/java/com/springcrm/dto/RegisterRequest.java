package com.springcrm.dto;

import com.springcrm.models.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO for user registration request
 */
public class RegisterRequest {
    
    @NotBlank
    @Size(min = 3, max = 100)
    private String username;
    
    @Email
    @NotBlank
    private String email;
    
    @NotBlank
    @Size(min = 6)
    private String password;
    
    private String userrole = "user";
    
    // Constructors
    public RegisterRequest() {}
    
    public RegisterRequest(String username, String email, String password, String userrole) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.userrole = userrole;
    }
    
    // Getters and Setters
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getUserrole() {
        return userrole;
    }
    
    public void setUserrole(String userrole) {
        this.userrole = userrole;
    }
    
    /**
     * Convert to User.UserRole enum
     */
    public User.UserRole getRole() {
        return User.UserRole.fromString(userrole);
    }
}
