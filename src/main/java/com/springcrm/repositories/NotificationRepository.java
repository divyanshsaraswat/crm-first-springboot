package com.springcrm.repositories;

import com.springcrm.models.Notification;
import com.springcrm.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repository interface for Notification entity operations.
 */
@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID> {
    
    /**
     * Find notifications by user
     */
    List<Notification> findByUser(User user);
    
    /**
     * Find notifications by user ID
     */
    List<Notification> findByUserId(UUID userId);
    
    /**
     * Find notifications by user and read status
     */
    List<Notification> findByUserAndIsRead(User user, Boolean isRead);
    
    /**
     * Find notifications by type
     */
    List<Notification> findByType(String type);
    
    /**
     * Find unread notifications for user
     */
    @Query("SELECT n FROM Notification n WHERE n.user.id = :userId AND n.isRead = false ORDER BY n.createdAt DESC")
    List<Notification> findUnreadNotificationsByUser(@Param("userId") UUID userId);
    
    /**
     * Find notifications by user and type
     */
    @Query("SELECT n FROM Notification n WHERE n.user.id = :userId AND n.type = :type ORDER BY n.createdAt DESC")
    List<Notification> findByUserAndType(@Param("userId") UUID userId, @Param("type") String type);
    
    /**
     * Count unread notifications for user
     */
    @Query("SELECT COUNT(n) FROM Notification n WHERE n.user.id = :userId AND n.isRead = false")
    Long countUnreadNotificationsByUser(@Param("userId") UUID userId);
}
