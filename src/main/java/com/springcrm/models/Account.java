package com.springcrm.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Account entity representing business accounts/customers in the CRM system.
 */
@Entity
@Table(name = "Accounts")
@EntityListeners(AuditingEntityListener.class)
public class Account {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @NotBlank
    @Column(name = "name", length = 70)
    private String name;
    
    @Column(name = "Rating", length = 70)
    private String rating;
    
    @Column(name = "ContPerson", length = 70)
    private String contactPerson;
    
    @Column(name = "Address1", length = 70)
    private String address1;
    
    @Column(name = "Address2", length = 70)
    private String address2;
    
    @Column(name = "City", length = 70)
    private String city;
    
    @Column(name = "Zone", length = 70)
    private String zone;
    
    @Column(name = "Zip", length = 70)
    private String zip;
    
    @Column(name = "States", length = 70)
    private String state;
    
    @Column(name = "Country", length = 70)
    private String country;
    
    @Column(name = "phone", length = 70)
    private String phone;
    
    @Column(name = "waphone", length = 70)
    private String whatsappPhone;
    
    @Column(name = "email", length = 70)
    private String email;
    
    @Column(name = "website", length = 70)
    private String website;
    
    @Column(name = "JoiningDate")
    private LocalDate joiningDate;
    
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;
    
    @Column(name = "SourceID")
    private UUID sourceId;
    
    @Column(name = "DesignationID")
    private UUID designationId;
    
    @Column(name = "BusinessNature", length = 70)
    private String businessNature;
    
    @Column(name = "FollowupID")
    private UUID followupId;
    
    @Column(name = "Descriptions", length = 150)
    private String descriptions;
    
    @Column(name = "StatusID")
    private UUID statusId;
    
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_user_id", nullable = false)
    private User assignedUser;
    
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_id", nullable = false)
    private User createdBy;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Relationships
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Contact> contacts;
    
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Opportunity> opportunities;
    
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AccountContact> accountContacts;
    
    // Constructors
    public Account() {}
    
    public Account(String name, Tenant tenant, User assignedUser, User createdBy) {
        this.name = name;
        this.tenant = tenant;
        this.assignedUser = assignedUser;
        this.createdBy = createdBy;
    }
    
    // Getters and Setters
    public UUID getId() {
        return id;
    }
    
    public void setId(UUID id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getRating() {
        return rating;
    }
    
    public void setRating(String rating) {
        this.rating = rating;
    }
    
    public String getContactPerson() {
        return contactPerson;
    }
    
    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }
    
    public String getAddress1() {
        return address1;
    }
    
    public void setAddress1(String address1) {
        this.address1 = address1;
    }
    
    public String getAddress2() {
        return address2;
    }
    
    public void setAddress2(String address2) {
        this.address2 = address2;
    }
    
    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public String getZone() {
        return zone;
    }
    
    public void setZone(String zone) {
        this.zone = zone;
    }
    
    public String getZip() {
        return zip;
    }
    
    public void setZip(String zip) {
        this.zip = zip;
    }
    
    public String getState() {
        return state;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    
    public String getCountry() {
        return country;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getWhatsappPhone() {
        return whatsappPhone;
    }
    
    public void setWhatsappPhone(String whatsappPhone) {
        this.whatsappPhone = whatsappPhone;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getWebsite() {
        return website;
    }
    
    public void setWebsite(String website) {
        this.website = website;
    }
    
    public LocalDate getJoiningDate() {
        return joiningDate;
    }
    
    public void setJoiningDate(LocalDate joiningDate) {
        this.joiningDate = joiningDate;
    }
    
    public Tenant getTenant() {
        return tenant;
    }
    
    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }
    
    public UUID getSourceId() {
        return sourceId;
    }
    
    public void setSourceId(UUID sourceId) {
        this.sourceId = sourceId;
    }
    
    public UUID getDesignationId() {
        return designationId;
    }
    
    public void setDesignationId(UUID designationId) {
        this.designationId = designationId;
    }
    
    public String getBusinessNature() {
        return businessNature;
    }
    
    public void setBusinessNature(String businessNature) {
        this.businessNature = businessNature;
    }
    
    public UUID getFollowupId() {
        return followupId;
    }
    
    public void setFollowupId(UUID followupId) {
        this.followupId = followupId;
    }
    
    public String getDescriptions() {
        return descriptions;
    }
    
    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }
    
    public UUID getStatusId() {
        return statusId;
    }
    
    public void setStatusId(UUID statusId) {
        this.statusId = statusId;
    }
    
    public User getAssignedUser() {
        return assignedUser;
    }
    
    public void setAssignedUser(User assignedUser) {
        this.assignedUser = assignedUser;
    }
    
    public User getCreatedBy() {
        return createdBy;
    }
    
    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public List<Contact> getContacts() {
        return contacts;
    }
    
    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }
    
    public List<Opportunity> getOpportunities() {
        return opportunities;
    }
    
    public void setOpportunities(List<Opportunity> opportunities) {
        this.opportunities = opportunities;
    }
    
    public List<AccountContact> getAccountContacts() {
        return accountContacts;
    }
    
    public void setAccountContacts(List<AccountContact> accountContacts) {
        this.accountContacts = accountContacts;
    }
}
