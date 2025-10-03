package com.springcrm.repositories;

import com.springcrm.models.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Repository interface for Campaign entity operations.
 */
@Repository
public interface CampaignRepository extends JpaRepository<Campaign, UUID> {
    
    /**
     * Find campaigns by status
     */
    List<Campaign> findByStatus(String status);
    
    /**
     * Find campaigns by name containing (case insensitive)
     */
    List<Campaign> findByNameContainingIgnoreCase(String name);
    
    /**
     * Find campaigns by start date
     */
    List<Campaign> findByStartDate(LocalDate startDate);
    
    /**
     * Find campaigns by end date
     */
    List<Campaign> findByEndDate(LocalDate endDate);
    
    /**
     * Find active campaigns (between start and end date)
     */
    @Query("SELECT c FROM Campaign c WHERE c.startDate <= :currentDate AND c.endDate >= :currentDate")
    List<Campaign> findActiveCampaigns(@Param("currentDate") LocalDate currentDate);
    
    /**
     * Find campaigns starting soon
     */
    @Query("SELECT c FROM Campaign c WHERE c.startDate BETWEEN :startDate AND :endDate")
    List<Campaign> findCampaignsStartingBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    /**
     * Find campaigns ending soon
     */
    @Query("SELECT c FROM Campaign c WHERE c.endDate BETWEEN :startDate AND :endDate")
    List<Campaign> findCampaignsEndingBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
