package com.example.Crud.service;

import com.example.Crud.dto.CreatePersonDto;
import com.example.Crud.dto.UpdatePersonDto;
import com.example.Crud.entity.PersonEntity;
import com.example.Crud.repostiory.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.example.Crud.dto.Mapper.dtoToEntity;
import static org.apache.kafka.common.requests.DeleteAclsResponse.log;

@EnableKafka
@Service
public class PersonService {

    @Autowired
    private ReactiveRedisTemplate<String, String> redisTemplate;

    private static final String SUCCESSFUL_UPDATES_KEY = "successful_updates";

    private final PersonRepository repository;

    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    public Flux<PersonEntity> getAllPeople() {
        return repository.findAll();
    }

    public Mono<PersonEntity> getPersonById(UUID id) {
        return repository.findById(id);
    }

    public Mono<PersonEntity> create(CreatePersonDto dto) {
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



    public Mono<PersonEntity> updatePersonWithRandomData(UUID id) {
        return repository.findById(id)
                .flatMap(person -> {
                    person.setFirstName(RandomDataGenerator.getRandomFirstName());
                    person.setLastName(RandomDataGenerator.getRandomLastName());

                    return repository.save(person);
                });
    }



    @KafkaListener(topics = "personUpdatesTopic", groupId = "person-update-group")
    public void listenForRandomPersonUpdates(String personIdString) {
        try {
            UUID personId = UUID.fromString(personIdString);
            attemptToUpdatePerson(personId);
        } catch (IllegalArgumentException e) {
            log.error("Invalid UUID format in Kafka message: {}", personIdString, e);
        } catch (Exception e) {
            log.error("Error processing Kafka message for random update", e);
        }
    }

    // update randomly
    public void attemptToUpdatePerson(UUID personId) {
        String lockKey = "person_lock:" + personId;

        redisTemplate.opsForValue().setIfAbsent(lockKey, "locked", Duration.ofSeconds(30))
                .flatMap(acquired -> {
                    if (Boolean.TRUE.equals(acquired)) {
                        return updatePersonWithRandomData(personId)
                                .doOnSuccess(updatedPerson -> {
                                    incrementSuccessfulUpdates();
                                })
                                .doFinally(signalType -> {
                                    redisTemplate.delete(lockKey).subscribe();

                                });

                    } else {
                        log.info("Lock not acquired for personId: {}, skipping update", personId);
                        return Mono.empty();
                    }
                })
                .subscribe(
                        updatedPerson -> log.info("Person updated with random data: {}", updatedPerson),
                        error -> log.error("Error updating person with random data", error)
                );
    }


    private void incrementSuccessfulUpdates() {
        redisTemplate.opsForValue().increment(SUCCESSFUL_UPDATES_KEY).subscribe(
                success -> log.debug("Incremented successful updates."),
                error -> log.error("Failed to increment successful updates", error)
        );
    }

    @Scheduled(fixedRate = 60000)
    public void reportSuccessfulUpdates() {
        redisTemplate.opsForValue().getAndSet(SUCCESSFUL_UPDATES_KEY, "0")
                .defaultIfEmpty("0")
                .subscribe(count -> log.info("Successful updates in the last 1 minute: {}", count),
                        error -> log.error("Error while reporting successful updates", error));
    }


    // for random updates w/o request
//    @Scheduled(fixedRate = 10000)
    public void randomlyUpdatePerson() {
        UUID somePersonId = UUID.fromString("d756bd3d-5961-4191-a64d-ceb06ac2658e");
        updatePersonWithRandomData(somePersonId).subscribe(
                updatedPerson -> System.out.println("Person updated with random data: " + updatedPerson),
                error -> System.err.println("Error updating person with random data: " + error)
        );
    }


}
