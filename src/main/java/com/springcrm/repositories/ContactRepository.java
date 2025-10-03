package com.springcrm.repositories;

import com.springcrm.models.Contact;
import com.springcrm.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repository interface for Contact entity operations.
 */
@Repository
public interface ContactRepository extends JpaRepository<Contact, UUID> {
    
    /**
     * Find contacts by contact owner
     */
    List<Contact> findByContactOwner(User contactOwner);
    
    /**
     * Find contacts by account ID
     */
    List<Contact> findByAccountId(UUID accountId);
    
    /**
     * Find contacts by email
     */
    List<Contact> findByEmail(String email);
    
    /**
     * Find contacts by last name containing (case insensitive)
     */
    List<Contact> findByLastNameContainingIgnoreCase(String lastName);
    
    /**
     * Find contacts by first name and last name
     */
    List<Contact> findByFirstNameAndLastName(String firstName, String lastName);
    
    /**
     * Find contacts by contact owner and account
     */
    @Query("SELECT c FROM Contact c WHERE c.contactOwner.id = :ownerId AND c.account.id = :accountId")
    List<Contact> findByContactOwnerAndAccount(@Param("ownerId") UUID ownerId, @Param("accountId") UUID accountId);
    
    /**
     * Find contacts by account and name containing
     */
    @Query("SELECT c FROM Contact c WHERE c.account.id = :accountId AND " +
           "(LOWER(c.firstName) LIKE LOWER(CONCAT('%', :name, '%')) OR " +
           "LOWER(c.lastName) LIKE LOWER(CONCAT('%', :name, '%')))")
    List<Contact> findByAccountAndNameContaining(@Param("accountId") UUID accountId, @Param("name") String name);
}
