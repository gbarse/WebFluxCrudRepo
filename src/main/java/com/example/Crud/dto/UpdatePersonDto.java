package com.example.Crud.dto;

import org.springframework.data.annotation.Version;

import java.util.UUID;

public class UpdatePersonDto {

    private UUID id;
    private String firstName;
    private String lastName;

    @Version
    private Integer version;

    public UpdatePersonDto() {
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
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
}
