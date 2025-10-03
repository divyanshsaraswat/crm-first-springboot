package com.springcrm.repositories;

import com.springcrm.models.Opportunity;
import com.springcrm.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Repository interface for Opportunity entity operations.
 */
@Repository
public interface OpportunityRepository extends JpaRepository<Opportunity, UUID> {
    
    /**
     * Find opportunities by assigned user
     */
    List<Opportunity> findByAssignedUser(User assignedUser);
    
    /**
     * Find opportunities by account ID
     */
    List<Opportunity> findByAccountId(UUID accountId);
    
    /**
     * Find opportunities by contact ID
     */
    List<Opportunity> findByContactId(UUID contactId);
    
    /**
     * Find opportunities by stage
     */
    List<Opportunity> findByStage(String stage);
    
    /**
     * Find opportunities by close date
     */
    List<Opportunity> findByCloseDate(LocalDate closeDate);
    
    /**
     * Find opportunities by close date before
     */
    List<Opportunity> findByCloseDateBefore(LocalDate closeDate);
    
    /**
     * Find opportunities by assigned user and stage
     */
    List<Opportunity> findByAssignedUserAndStage(User assignedUser, String stage);
    
    /**
     * Find opportunities by name containing (case insensitive)
     */
    List<Opportunity> findByNameContainingIgnoreCase(String name);
    
    /**
     * Find opportunities with amount greater than
     */
    List<Opportunity> findByAmountGreaterThan(BigDecimal amount);
    
    /**
     * Find opportunities by account and stage
     */
    @Query("SELECT o FROM Opportunity o WHERE o.account.id = :accountId AND o.stage = :stage")
    List<Opportunity> findByAccountAndStage(@Param("accountId") UUID accountId, @Param("stage") String stage);
    
    /**
     * Find opportunities closing soon
     */
    @Query("SELECT o FROM Opportunity o WHERE o.closeDate BETWEEN :startDate AND :endDate")
    List<Opportunity> findOpportunitiesClosingBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
