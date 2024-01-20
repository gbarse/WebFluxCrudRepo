package com.example.Crud.service;

import com.example.Crud.dto.CreatePersonDto;
import com.example.Crud.dto.UpdatePersonDto;
import com.example.Crud.entity.PersonEntity;
import com.example.Crud.repostiory.PersonRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.example.Crud.dto.Mapper.dtoToEntity;

@Service
public class PersonService {

    private final PersonRepository repository;

    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    public Flux<PersonEntity> getAllPeople(){
        return repository.findAll();
    }

    public Mono<PersonEntity> getPersonById(UUID id){
        return repository.findById(id);
    }

    public Mono<PersonEntity> create(CreatePersonDto dto){
        PersonEntity entity = dtoToEntity(dto);
        return repository.save(entity);
    }

    public Mono<PersonEntity> updatePerson(UUID id, UpdatePersonDto updateDTO) {
        return repository.findById(id)
                .flatMap(existingPerson -> {
                    if (updateDTO.getFirstName() != null) {
                        existingPerson.setFirstName(updateDTO.getFirstName());
                    }
                    if (updateDTO.getLastName() != null) {
                        existingPerson.setLastName(updateDTO.getLastName());
                    }
                    existingPerson.setDateUpdated(LocalDateTime.now());
                    return repository.save(existingPerson);
                });
    }

    public Mono<Void> delete(UUID id) {
        return repository.findById(id)
                .flatMap(entity -> {
                    entity.setDateDeleted(LocalDateTime.now());
                    return repository.save(entity);
                })
                .then();
    }

}
