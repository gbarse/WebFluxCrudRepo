package com.example.Crud.repostiory;

import com.example.Crud.entity.PersonEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PersonRepository extends ReactiveMongoRepository<PersonEntity, UUID> {
}
