package com.springcrm.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * User entity representing system users with role-based access control.
 * Supports hierarchical user structure with parent-child relationships.
 */
@Entity
@Table(name = "Users")
@EntityListeners(AuditingEntityListener.class)
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @NotBlank
    @Column(name = "username", nullable = false, length = 100)
    private String username;
    
    @Email
    @NotBlank
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    
    @Column(name = "parent_id")
    private UUID parentId;
    
    @NotBlank
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserRole role = UserRole.USER;
    
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Relationships
    @OneToMany(mappedBy = "assignedUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Account> assignedAccounts;
    
    @OneToMany(mappedBy = "assignedUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Task> assignedTasks;
    
    @OneToMany(mappedBy = "assignedUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Lead> assignedLeads;
    
    @OneToMany(mappedBy = "assignedUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Opportunity> assignedOpportunities;
    
    @OneToMany(mappedBy = "assignedUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Email> assignedEmails;
    
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private UserSettings userSettings;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Notification> notifications;
    
    // Constructors
    public User() {}
    
    public User(String username, String email, String passwordHash, UserRole role, Tenant tenant) {
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        this.tenant = tenant;
    }
    
    // Getters and Setters
    public UUID getId() {
        return id;
    }
    
    public void setId(UUID id) {
        this.id = id;
    }
    
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
    
    public UUID getParentId() {
        return parentId;
    }
    
    public void setParentId(UUID parentId) {
        this.parentId = parentId;
    }
    
    public String getPasswordHash() {
        return passwordHash;
    }
    
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
    
    public UserRole getRole() {
        return role;
    }
    
    public void setRole(UserRole role) {
        this.role = role;
    }
    
    public Tenant getTenant() {
        return tenant;
    }
    
    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public List<Account> getAssignedAccounts() {
        return assignedAccounts;
    }
    
    public void setAssignedAccounts(List<Account> assignedAccounts) {
        this.assignedAccounts = assignedAccounts;
    }
    
    public List<Task> getAssignedTasks() {
        return assignedTasks;
    }
    
    public void setAssignedTasks(List<Task> assignedTasks) {
        this.assignedTasks = assignedTasks;
    }
    
    public List<Lead> getAssignedLeads() {
        return assignedLeads;
    }
    
    public void setAssignedLeads(List<Lead> assignedLeads) {
        this.assignedLeads = assignedLeads;
    }
    
    public List<Opportunity> getAssignedOpportunities() {
        return assignedOpportunities;
    }
    
    public void setAssignedOpportunities(List<Opportunity> assignedOpportunities) {
        this.assignedOpportunities = assignedOpportunities;
    }
    
    public List<Email> getAssignedEmails() {
        return assignedEmails;
    }
    
    public void setAssignedEmails(List<Email> assignedEmails) {
        this.assignedEmails = assignedEmails;
    }
    
    public UserSettings getUserSettings() {
        return userSettings;
    }
    
    public void setUserSettings(UserSettings userSettings) {
        this.userSettings = userSettings;
    }
    
    public List<Notification> getNotifications() {
        return notifications;
    }
    
    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }
    
    /**
     * User roles enum matching the Node.js implementation
     */
    public enum UserRole {
        ADMIN("admin"),
        MANAGER("manager"),
        USER("user"),
        GUEST("guest");
        
        private final String value;
        
        UserRole(String value) {
            this.value = value;
        }
        
        public String getValue() {
            return value;
        }
        
        public static UserRole fromString(String value) {
            for (UserRole role : UserRole.values()) {
                if (role.value.equalsIgnoreCase(value)) {
                    return role;
                }
            }
            throw new IllegalArgumentException("Unknown role: " + value);
        }
    }
}
