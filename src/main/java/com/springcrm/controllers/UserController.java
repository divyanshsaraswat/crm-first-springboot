package com.springcrm.controllers;

import com.springcrm.dto.ApiResponse;
import com.springcrm.dto.LoginRequest;
import com.springcrm.dto.RegisterRequest;
import com.springcrm.models.User;
import com.springcrm.models.UserSettings;
import com.springcrm.models.Notification;
import com.springcrm.services.UserService;
import com.springcrm.security.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * REST Controller for User operations.
 * Replicates the Node.js userController functionality.
 */
@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "User management operations")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    /**
     * User login endpoint
     */
    @PostMapping("/login")
    @Operation(summary = "User login", description = "Authenticate user and return JWT token")
    public ResponseEntity<ApiResponse<String>> login(@Valid @RequestBody LoginRequest request, 
                                                   HttpServletResponse response) {
        try {
            Optional<User> userOpt = userService.authenticateUser(request.getEmail(), request.getPassword());
            
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                String token = jwtUtil.generateToken(
                    user.getEmail(), 
                    user.getRole().getValue(), 
                    user.getTenant().getId().toString(),
                    user.getId().toString()
                );
                
                // Set JWT token in cookie (replicating Node.js behavior)
                Cookie cookie = new Cookie("token", token);
                cookie.setHttpOnly(true);
                cookie.setSecure(false); // Set to true in production with HTTPS
                cookie.setPath("/");
                cookie.setMaxAge(24 * 60 * 60); // 24 hours
                response.addCookie(cookie);
                
                return ResponseEntity.ok(ApiResponse.success("Login Successful.", null));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.error("Invalid credentials"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Internal Server Error"));
        }
    }
    
    /**
     * User registration endpoint
     */
    @PostMapping("/register")
    @Operation(summary = "User registration", description = "Register a new user")
    public ResponseEntity<ApiResponse<String>> register(@Valid @RequestBody RegisterRequest request) {
        try {
            // For now, using a default tenant ID - you'll need to implement tenant management
            UUID defaultTenantId = UUID.randomUUID(); // Replace with actual tenant logic
            User user = userService.registerUser(request, defaultTenantId);
            
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("User registered successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Internal Server Error"));
        }
    }
    
    /**
     * Get all users (admin/manager only)
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @Operation(summary = "Get all users", description = "Retrieve all users (admin/manager only)")
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers(@RequestAttribute("tenantid") String tenantId) {
        try {
            List<User> users = userService.getAllUsers(UUID.fromString(tenantId));
            return ResponseEntity.ok(ApiResponse.success("Users retrieved successfully", users));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Internal Server Error"));
        }
    }
    
    /**
     * Get user by ID
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('USER')")
    @Operation(summary = "Get user by ID", description = "Retrieve user by ID")
    public ResponseEntity<ApiResponse<User>> getUserById(@PathVariable UUID id) {
        try {
            Optional<User> user = userService.getUserById(id);
            if (user.isPresent()) {
                return ResponseEntity.ok(ApiResponse.success("User retrieved successfully", user.get()));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("User not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Internal Server Error"));
        }
    }
    
    /**
     * Update user
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @Operation(summary = "Update user", description = "Update user information")
    public ResponseEntity<ApiResponse<String>> updateUser(@PathVariable UUID id,
                                                        @RequestBody RegisterRequest request) {
        try {
            userService.updateUser(id, request.getUsername(), request.getEmail(), request.getRole());
            return ResponseEntity.ok(ApiResponse.success("User updated successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Internal Server Error"));
        }
    }
    
    /**
     * Delete user
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete user", description = "Delete user (admin only)")
    public ResponseEntity<ApiResponse<String>> deleteUser(@PathVariable UUID id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok(ApiResponse.success("User deleted successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Internal Server Error"));
        }
    }
    
    /**
     * Change password
     */
    @PostMapping("/change-password")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('USER')")
    @Operation(summary = "Change password", description = "Change user password")
    public ResponseEntity<ApiResponse<String>> changePassword(@RequestAttribute("pid") String userId,
                                                            @RequestBody ChangePasswordRequest request) {
        try {
            boolean success = userService.changePassword(
                UUID.fromString(userId), 
                request.getOldPassword(), 
                request.getNewPassword()
            );
            
            if (success) {
                return ResponseEntity.ok(ApiResponse.success("Password changed successfully", null));
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(ApiResponse.error("Password do not match!"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Internal Server Error"));
        }
    }
    
    /**
     * Get user settings
     */
    @GetMapping("/settings")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('USER')")
    @Operation(summary = "Get user settings", description = "Get user settings")
    public ResponseEntity<ApiResponse<UserSettings>> getUserSettings(@RequestAttribute("pid") String userId) {
        try {
            UserSettings settings = userService.getUserSettings(UUID.fromString(userId));
            return ResponseEntity.ok(ApiResponse.success("Settings retrieved successfully", settings));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Internal Server Error"));
        }
    }
    
    /**
     * Update user settings
     */
    @PutMapping("/settings")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('USER')")
    @Operation(summary = "Update user settings", description = "Update user settings")
    public ResponseEntity<ApiResponse<UserSettings>> updateUserSettings(@RequestAttribute("pid") String userId,
                                                                      @RequestBody UserSettings settings) {
        try {
            UserSettings updatedSettings = userService.updateUserSettings(UUID.fromString(userId), settings);
            return ResponseEntity.ok(ApiResponse.success("Settings updated successfully", updatedSettings));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Internal Server Error"));
        }
    }
    
    /**
     * Get all notifications
     */
    @GetMapping("/notifications")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('USER')")
    @Operation(summary = "Get notifications", description = "Get all notifications for user")
    public ResponseEntity<ApiResponse<List<Notification>>> getAllNotifications(@RequestAttribute("pid") String userId) {
        try {
            List<Notification> notifications = userService.getAllNotifications(UUID.fromString(userId));
            return ResponseEntity.ok(ApiResponse.success("Notifications retrieved successfully", notifications));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Internal Server Error"));
        }
    }
    
    /**
     * Create notification
     */
    @PostMapping("/notifications")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('USER')")
    @Operation(summary = "Create notification", description = "Create a new notification")
    public ResponseEntity<ApiResponse<String>> createNotification(@RequestAttribute("pid") String userId,
                                                                @RequestBody CreateNotificationRequest request) {
        try {
            userService.createNotification(
                UUID.fromString(userId), 
                request.getTitle(), 
                request.getMessage(), 
                request.getType()
            );
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Notification created successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Internal Server Error"));
        }
    }
    
    /**
     * Mark notification as read
     */
    @PatchMapping("/notifications/{id}/read")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('USER')")
    @Operation(summary = "Mark notification as read", description = "Mark notification as read")
    public ResponseEntity<ApiResponse<String>> markNotificationAsRead(@PathVariable UUID id) {
        try {
            userService.markNotificationAsRead(id);
            return ResponseEntity.ok(ApiResponse.success("Notification marked as read", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Internal Server Error"));
        }
    }
    
    /**
     * Delete notification
     */
    @DeleteMapping("/notifications/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('USER')")
    @Operation(summary = "Delete notification", description = "Delete notification")
    public ResponseEntity<ApiResponse<String>> deleteNotification(@PathVariable UUID id) {
        try {
            userService.deleteNotification(id);
            return ResponseEntity.ok(ApiResponse.success("Notification deleted successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Internal Server Error"));
        }
    }
    
    /**
     * Get user roles
     */
    @GetMapping("/roles")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('USER')")
    @Operation(summary = "Get user roles", description = "Get available user roles")
    public ResponseEntity<ApiResponse<List<String>>> getUserRoles() {
        try {
            List<String> roles = userService.getUserRoles();
            return ResponseEntity.ok(ApiResponse.success("Roles retrieved successfully", roles));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Internal Server Error"));
        }
    }
    
    // Inner classes for request DTOs
    public static class ChangePasswordRequest {
        private String oldPassword;
        private String newPassword;
        
        // Getters and setters
        public String getOldPassword() { return oldPassword; }
        public void setOldPassword(String oldPassword) { this.oldPassword = oldPassword; }
        public String getNewPassword() { return newPassword; }
        public void setNewPassword(String newPassword) { this.newPassword = newPassword; }
    }
    
    public static class CreateNotificationRequest {
        private String title;
        private String message;
        private String type = "general";
        
        // Getters and setters
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
    }
}
