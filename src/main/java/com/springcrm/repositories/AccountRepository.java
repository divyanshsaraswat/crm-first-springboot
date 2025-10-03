package com.springcrm.repositories;

import com.springcrm.models.Account;
import com.springcrm.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repository interface for Account entity operations.
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
    
    /**
     * Find accounts by assigned user
     */
    List<Account> findByAssignedUser(User assignedUser);
    
    /**
     * Find accounts by tenant ID
     */
    List<Account> findByTenantId(UUID tenantId);
    
    /**
     * Find accounts by created by user
     */
    List<Account> findByCreatedById(UUID createdById);
    
    /**
     * Find accounts by name containing (case insensitive)
     */
    List<Account> findByNameContainingIgnoreCase(String name);
    
    /**
     * Find accounts by email
     */
    List<Account> findByEmail(String email);
    
    /**
     * Find accounts by assigned user and tenant
     */
    @Query("SELECT a FROM Account a WHERE a.assignedUser.id = :userId AND a.tenant.id = :tenantId")
    List<Account> findByAssignedUserAndTenant(@Param("userId") UUID userId, @Param("tenantId") UUID tenantId);
    
    /**
     * Find accounts by tenant and name containing
     */
    @Query("SELECT a FROM Account a WHERE a.tenant.id = :tenantId AND LOWER(a.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Account> findByTenantAndNameContaining(@Param("tenantId") UUID tenantId, @Param("name") String name);
}
