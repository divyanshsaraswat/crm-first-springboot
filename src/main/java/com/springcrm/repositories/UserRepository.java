package com.springcrm.repositories;

import com.springcrm.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for User entity operations.
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    
    /**
     * Find user by email address
     */
    Optional<User> findByEmail(String email);
    
    /**
     * Find users by tenant ID
     */
    List<User> findByTenantId(UUID tenantId);
    
    /**
     * Find users by role
     */
    List<User> findByRole(User.UserRole role);
    
    /**
     * Find users by tenant ID and role
     */
    List<User> findByTenantIdAndRole(UUID tenantId, User.UserRole role);
    
    /**
     * Check if email exists
     */
    boolean existsByEmail(String email);
    
    /**
     * Find users by parent ID (hierarchical structure)
     */
    List<User> findByParentId(UUID parentId);
    
    /**
     * Find users with specific role in tenant
     */
    @Query("SELECT u FROM User u WHERE u.tenant.id = :tenantId AND u.role = :role")
    List<User> findUsersByTenantAndRole(@Param("tenantId") UUID tenantId, @Param("role") User.UserRole role);
    
    /**
     * Find all users except the specified user
     */
    @Query("SELECT u FROM User u WHERE u.id != :userId")
    List<User> findAllExceptUser(@Param("userId") UUID userId);
}
