package com.example.Crud.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;


@Document
public class PersonEntity {
    @Id
    private UUID id;
    private String firstName;
    private String lastName;
    @CreatedDate
    private LocalDateTime dateCreated;
    @LastModifiedDate
    private LocalDateTime dateUpdated;
    private LocalDateTime dateDeleted;

    public PersonEntity() {
    }

    public PersonEntity(UUID id, String firstName, String lastName, LocalDateTime dateCreated, LocalDateTime dateUpdated, LocalDateTime dateDeleted) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
        this.dateDeleted = dateDeleted;
    }

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

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDateTime getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(LocalDateTime dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public LocalDateTime getDateDeleted() {
        return dateDeleted;
    }

    public void setDateDeleted(LocalDateTime dateDeleted) {
        this.dateDeleted = dateDeleted;
    }
}
