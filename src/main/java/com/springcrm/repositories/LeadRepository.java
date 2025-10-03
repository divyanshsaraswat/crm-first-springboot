package com.springcrm.repositories;

import com.springcrm.models.Lead;
import com.springcrm.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repository interface for Lead entity operations.
 */
@Repository
public interface LeadRepository extends JpaRepository<Lead, UUID> {
    
    /**
     * Find leads by assigned user
     */
    List<Lead> findByAssignedUser(User assignedUser);
    
    /**
     * Find leads by status
     */
    List<Lead> findByStatus(String status);
    
    /**
     * Find leads by email
     */
    List<Lead> findByEmail(String email);
    
    /**
     * Find leads by last name containing (case insensitive)
     */
    List<Lead> findByLastNameContainingIgnoreCase(String lastName);
    
    /**
     * Find leads by assigned user and status
     */
    List<Lead> findByAssignedUserAndStatus(User assignedUser, String status);
    
    /**
     * Find leads by contact ID
     */
    List<Lead> findByContactId(UUID contactId);
    
    /**
     * Find leads by first name and last name
     */
    List<Lead> findByFirstNameAndLastName(String firstName, String lastName);
    
    /**
     * Find leads by name containing (first or last name)
     */
    @Query("SELECT l FROM Lead l WHERE " +
           "LOWER(l.firstName) LIKE LOWER(CONCAT('%', :name, '%')) OR " +
           "LOWER(l.lastName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Lead> findByNameContaining(@Param("name") String name);
}
