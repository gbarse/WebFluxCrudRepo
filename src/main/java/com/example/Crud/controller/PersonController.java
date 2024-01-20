package com.example.Crud.controller;

import com.example.Crud.dto.CreatePersonDto;
import com.example.Crud.dto.UpdatePersonDto;
import com.example.Crud.entity.PersonEntity;
import com.example.Crud.service.PersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/people")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public Flux<PersonEntity> getAllPeople() {
        return personService.getAllPeople();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<PersonEntity>> getPersonById(@PathVariable UUID id) {
        return personService.getPersonById(id)
                .map(person -> ResponseEntity.ok(person))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<PersonEntity> createPerson(@RequestBody CreatePersonDto personDTO) {
        return personService.create(personDTO);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<PersonEntity>> updatePerson(@PathVariable UUID id, @RequestBody UpdatePersonDto updateDTO) {
        return personService.updatePerson(id, updateDTO)
                .map(updatedPerson -> ResponseEntity.ok(updatedPerson))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deletePerson(@PathVariable UUID id) {
        return personService.delete(id)
                .then(Mono.just(ResponseEntity.ok().<Void>build()))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
