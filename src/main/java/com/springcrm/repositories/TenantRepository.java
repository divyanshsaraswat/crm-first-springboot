package com.springcrm.repositories;

import com.springcrm.models.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for Tenant entity operations.
 */
@Repository
public interface TenantRepository extends JpaRepository<Tenant, UUID> {
    
    /**
     * Find tenant by email address
     */
    Optional<Tenant> findByEmail(String email);
    
    /**
     * Check if email exists
     */
    boolean existsByEmail(String email);
}
