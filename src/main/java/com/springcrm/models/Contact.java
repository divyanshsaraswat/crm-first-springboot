package com.springcrm.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Contact entity representing individual contacts associated with accounts.
 */
@Entity
@Table(name = "Contacts")
@EntityListeners(AuditingEntityListener.class)
public class Contact {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(name = "first_name", length = 100)
    private String firstName;
    
    @NotBlank
    @Column(name = "last_name", length = 100, nullable = false)
    private String lastName;
    
    @Email
    @Column(name = "email", length = 255)
    private String email;
    
    @Column(name = "phone", length = 50)
    private String phone;
    
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
    
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_owner_id", nullable = false)
    private User contactOwner;
    
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Relationships
    @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Task> tasks;
    
    @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Email> emails;
    
    @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Opportunity> opportunities;
    
    @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AccountContact> accountContacts;
    
    // Constructors
    public Contact() {}
    
    public Contact(String lastName, Account account, User contactOwner) {
        this.lastName = lastName;
        this.account = account;
        this.contactOwner = contactOwner;
    }
    
    // Getters and Setters
    public UUID getId() {
        return id;
    }
    
    public void setId(UUID id) {
        this.id = id;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public Account getAccount() {
        return account;
    }
    
    public void setAccount(Account account) {
        this.account = account;
    }
    
    public User getContactOwner() {
        return contactOwner;
    }
    
    public void setContactOwner(User contactOwner) {
        this.contactOwner = contactOwner;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public List<Task> getTasks() {
        return tasks;
    }
    
    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
    
    public List<Email> getEmails() {
        return emails;
    }
    
    public void setEmails(List<Email> emails) {
        this.emails = emails;
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
    
    /**
     * Get full name by combining first and last name
     */
    public String getFullName() {
        if (firstName == null || firstName.isEmpty()) {
            return lastName;
        }
        return firstName + " " + lastName;
    }
}
