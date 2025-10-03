package com.springcrm.models;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * User settings entity for storing user preferences and notification settings.
 */
@Entity
@Table(name = "user_settings")
@EntityListeners(AuditingEntityListener.class)
public class UserSettings {
    
    @Id
    @Column(name = "user_id")
    private UUID userId;
    
    // Notification preferences
    @Column(name = "notify_email")
    private Boolean notifyEmail = true;
    
    @Column(name = "notify_browser")
    private Boolean notifyBrowser = true;
    
    @Column(name = "notify_lead_alerts")
    private Boolean notifyLeadAlerts = true;
    
    @Column(name = "notify_task_reminders")
    private Boolean notifyTaskReminders = true;
    
    // General preferences
    @Column(name = "date_format")
    private String dateFormat = "YYYY-MM-DD";
    
    @Column(name = "time_format")
    private String timeFormat = "24h";
    
    @Column(name = "currency")
    private String currency = "USD";
    
    @Column(name = "theme")
    private String theme = "light";
    
    @Column(name = "auto_refresh_interval")
    private Integer autoRefreshInterval = 60; // minutes
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @MapsId
    private User user;
    
    // Constructors
    public UserSettings() {}
    
    public UserSettings(User user) {
        this.user = user;
        this.userId = user.getId();
    }
    
    // Getters and Setters
    public UUID getUserId() {
        return userId;
    }
    
    public void setUserId(UUID userId) {
        this.userId = userId;
    }
    
    public Boolean getNotifyEmail() {
        return notifyEmail;
    }
    
    public void setNotifyEmail(Boolean notifyEmail) {
        this.notifyEmail = notifyEmail;
    }
    
    public Boolean getNotifyBrowser() {
        return notifyBrowser;
    }
    
    public void setNotifyBrowser(Boolean notifyBrowser) {
        this.notifyBrowser = notifyBrowser;
    }
    
    public Boolean getNotifyLeadAlerts() {
        return notifyLeadAlerts;
    }
    
    public void setNotifyLeadAlerts(Boolean notifyLeadAlerts) {
        this.notifyLeadAlerts = notifyLeadAlerts;
    }
    
    public Boolean getNotifyTaskReminders() {
        return notifyTaskReminders;
    }
    
    public void setNotifyTaskReminders(Boolean notifyTaskReminders) {
        this.notifyTaskReminders = notifyTaskReminders;
    }
    
    public String getDateFormat() {
        return dateFormat;
    }
    
    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }
    
    public String getTimeFormat() {
        return timeFormat;
    }
    
    public void setTimeFormat(String timeFormat) {
        this.timeFormat = timeFormat;
    }
    
    public String getCurrency() {
        return currency;
    }
    
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    
    public String getTheme() {
        return theme;
    }
    
    public void setTheme(String theme) {
        this.theme = theme;
    }
    
    public Integer getAutoRefreshInterval() {
        return autoRefreshInterval;
    }
    
    public void setAutoRefreshInterval(Integer autoRefreshInterval) {
        this.autoRefreshInterval = autoRefreshInterval;
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
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
        this.userId = user.getId();
    }
}
