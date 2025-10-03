package com.springcrm.services;

import com.springcrm.dto.RegisterRequest;
import com.springcrm.models.User;
import com.springcrm.models.UserSettings;
import com.springcrm.models.Notification;
import com.springcrm.repositories.UserRepository;
import com.springcrm.repositories.NotificationRepository;
import com.springcrm.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service class for User-related business logic.
 * Replicates the Node.js userService functionality.
 */
@Service
@Transactional
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private NotificationRepository notificationRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private CustomUserDetailsService userDetailsService;
    
    /**
     * Register a new user
     */
    public User registerUser(RegisterRequest request, UUID tenantId) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("User with email already exists");
        }
        
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        
        // Set tenant (you'll need to fetch this from database)
        // user.setTenant(tenant);
        
        User savedUser = userRepository.save(user);
        
        // Create default user settings
        UserSettings settings = new UserSettings(savedUser);
        savedUser.setUserSettings(settings);
        
        return savedUser;
    }
    
    /**
     * Authenticate user login
     */
    public Optional<User> authenticateUser(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (passwordEncoder.matches(password, user.getPasswordHash())) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }
    
    /**
     * Get all users (admin/manager only)
     */
    public List<User> getAllUsers(UUID tenantId) {
        return userRepository.findByTenantId(tenantId);
    }
    
    /**
     * Get user by ID
     */
    public Optional<User> getUserById(UUID id) {
        return userRepository.findById(id);
    }
    
    /**
     * Update user
     */
    public User updateUser(UUID id, String username, String email, User.UserRole role) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        user.setUsername(username);
        user.setEmail(email);
        user.setRole(role);
        
        return userRepository.save(user);
    }
    
    /**
     * Delete user
     */
    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }
    
    /**
     * Change user password
     */
    public boolean changePassword(UUID userId, String oldPassword, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        if (passwordEncoder.matches(oldPassword, user.getPasswordHash())) {
            user.setPasswordHash(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            return true;
        }
        return false;
    }
    
    /**
     * Get user settings
     */
    public UserSettings getUserSettings(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        return user.getUserSettings();
    }
    
    /**
     * Update user settings
     */
    public UserSettings updateUserSettings(UUID userId, UserSettings settings) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        UserSettings userSettings = user.getUserSettings();
        if (userSettings == null) {
            userSettings = new UserSettings(user);
        }
        
        userSettings.setNotifyEmail(settings.getNotifyEmail());
        userSettings.setNotifyBrowser(settings.getNotifyBrowser());
        userSettings.setNotifyLeadAlerts(settings.getNotifyLeadAlerts());
        userSettings.setNotifyTaskReminders(settings.getNotifyTaskReminders());
        userSettings.setDateFormat(settings.getDateFormat());
        userSettings.setTimeFormat(settings.getTimeFormat());
        userSettings.setCurrency(settings.getCurrency());
        userSettings.setTheme(settings.getTheme());
        userSettings.setAutoRefreshInterval(settings.getAutoRefreshInterval());
        
        user.setUserSettings(userSettings);
        userRepository.save(user);
        
        return userSettings;
    }
    
    /**
     * Get all notifications for user
     */
    public List<Notification> getAllNotifications(UUID userId) {
        return notificationRepository.findByUserId(userId);
    }
    
    /**
     * Create notification
     */
    public Notification createNotification(UUID userId, String title, String message, String type) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Notification notification = new Notification(user, title, message, type);
        return notificationRepository.save(notification);
    }
    
    /**
     * Mark notification as read
     */
    public void markNotificationAsRead(UUID notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        
        notification.setIsRead(true);
        notificationRepository.save(notification);
    }
    
    /**
     * Delete notification
     */
    public void deleteNotification(UUID notificationId) {
        if (!notificationRepository.existsById(notificationId)) {
            throw new RuntimeException("Notification not found");
        }
        notificationRepository.deleteById(notificationId);
    }
    
    /**
     * Get user roles
     */
    public List<String> getUserRoles() {
        return List.of("admin", "manager", "user", "guest");
    }
}
