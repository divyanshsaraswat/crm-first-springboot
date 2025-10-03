package com.springcrm.repositories;

import com.springcrm.models.Email;
import com.springcrm.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Repository interface for Email entity operations.
 */
@Repository
public interface EmailRepository extends JpaRepository<Email, UUID> {
    
    /**
     * Find emails by assigned user
     */
    List<Email> findByAssignedUser(User assignedUser);
    
    /**
     * Find emails by contact ID
     */
    List<Email> findByContactId(UUID contactId);
    
    /**
     * Find emails by sender email
     */
    List<Email> findBySenderEmail(String senderEmail);
    
    /**
     * Find emails by recipient email
     */
    List<Email> findByRecipientEmail(String recipientEmail);
    
    /**
     * Find emails by subject containing (case insensitive)
     */
    List<Email> findBySubjectContainingIgnoreCase(String subject);
    
    /**
     * Find emails by sent date
     */
    List<Email> findBySentAt(LocalDateTime sentAt);
    
    /**
     * Find emails by sent date between
     */
    List<Email> findBySentAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * Find emails by assigned user and contact
     */
    @Query("SELECT e FROM Email e WHERE e.assignedUser.id = :userId AND e.contact.id = :contactId")
    List<Email> findByAssignedUserAndContact(@Param("userId") UUID userId, @Param("contactId") UUID contactId);
    
    /**
     * Find unsent emails
     */
    @Query("SELECT e FROM Email e WHERE e.sentAt IS NULL")
    List<Email> findUnsentEmails();
}
