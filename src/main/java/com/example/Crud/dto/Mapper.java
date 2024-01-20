package com.example.Crud.dto;

import com.example.Crud.entity.PersonEntity;


import java.time.LocalDateTime;
import java.util.UUID;

public class Mapper {
    public static PersonEntity dtoToEntity(CreatePersonDto dto) {
        PersonEntity entity = new PersonEntity();
        entity.setId(UUID.randomUUID());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setDateCreated(LocalDateTime.now());

        return entity;
    }

    public static PersonEntity dtoToEntity(UpdatePersonDto dto) {
        PersonEntity entity = new PersonEntity();
        entity.setId(UUID.randomUUID());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setDateCreated(LocalDateTime.now());

        return entity;

    }
}
