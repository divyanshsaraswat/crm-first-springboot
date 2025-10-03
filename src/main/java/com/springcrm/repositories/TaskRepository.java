package com.springcrm.repositories;

import com.springcrm.models.Task;
import com.springcrm.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Repository interface for Task entity operations.
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {
    
    /**
     * Find tasks by assigned user
     */
    List<Task> findByAssignedUser(User assignedUser);
    
    /**
     * Find tasks by created by user
     */
    List<Task> findByCreatedById(UUID createdById);
    
    /**
     * Find tasks by status
     */
    List<Task> findByStatus(String status);
    
    /**
     * Find tasks by due date
     */
    List<Task> findByDueDate(LocalDate dueDate);
    
    /**
     * Find tasks by due date before
     */
    List<Task> findByDueDateBefore(LocalDate dueDate);
    
    /**
     * Find tasks by assigned user and status
     */
    List<Task> findByAssignedUserAndStatus(User assignedUser, String status);
    
    /**
     * Find overdue tasks for a user
     */
    @Query("SELECT t FROM Task t WHERE t.assignedUser.id = :userId AND t.dueDate < :currentDate AND t.status != 'COMPLETED'")
    List<Task> findOverdueTasksForUser(@Param("userId") UUID userId, @Param("currentDate") LocalDate currentDate);
    
    /**
     * Find tasks by contact ID
     */
    List<Task> findByContactId(UUID contactId);
    
    /**
     * Find tasks by subject containing (case insensitive)
     */
    List<Task> findBySubjectContainingIgnoreCase(String subject);
}
