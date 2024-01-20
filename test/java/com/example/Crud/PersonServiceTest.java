package com.example.Crud;


import com.example.Crud.dto.CreatePersonDto;
import com.example.Crud.dto.UpdatePersonDto;
import com.example.Crud.entity.PersonEntity;
import com.example.Crud.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootTest
class PersonServiceTest {
    private MockPersonRepository mockRepository;
    private PersonService personService;

    @BeforeEach
    void setUp() {
        mockRepository = new MockPersonRepository();
        personService = new PersonService(mockRepository);
    }

    @Test
    void createPerson() {
        CreatePersonDto dto = new CreatePersonDto();
        dto.setFirstName("Gor");
        dto.setLastName("B");

        StepVerifier.create(personService.create(dto))
                .expectNextMatches(entity -> entity.getFirstName().equals("Gor") && entity.getLastName().equals("B"))
                .verifyComplete();
    }

    @Test
    void getAllPeople() {
        mockRepository.save(new PersonEntity(UUID.randomUUID(), "Gor", "B", LocalDateTime.now(), null, null)).block();

        StepVerifier.create(personService.getAllPeople())
                .expectNextMatches(foundPerson -> foundPerson.getFirstName().equals("Gor") && foundPerson.getLastName().equals("B"))
                .verifyComplete();
    }

    @Test
    void getPersonById() {
        UUID id = UUID.randomUUID();
        mockRepository.save(new PersonEntity(id, "Gor", "B", LocalDateTime.now(), null, null)).block();

        StepVerifier.create(personService.getPersonById(id))
                .expectNextMatches(foundPerson -> foundPerson.getId().equals(id))
                .verifyComplete();
    }

    @Test
    void updatePerson() {
        UUID id = UUID.randomUUID();
        mockRepository.save(new PersonEntity(id, "Gor", "B", LocalDateTime.now(), null, null)).block();
        UpdatePersonDto updateDTO = new UpdatePersonDto();
        updateDTO.setFirstName("Name1");

        StepVerifier.create(personService.updatePerson(id, updateDTO))
                .expectNextMatches(updatedPerson -> updatedPerson.getFirstName().equals("Name1") && updatedPerson.getLastName().equals("B"))
                .verifyComplete();
    }


    @Test
    void deletePersonShouldMarkPersonAsDeleted() {
        UUID id = UUID.randomUUID();
        mockRepository.save(new PersonEntity(id, "Gor", "B", LocalDateTime.now(), null, null)).block();
        StepVerifier.create(personService.delete(id))
                .verifyComplete();

        StepVerifier.create(mockRepository.findById(id))
                .expectNextMatches(foundPerson -> foundPerson.getDateDeleted() != null)
                .verifyComplete();
    }
}

