package com.example.Crud;

import com.example.Crud.entity.PersonEntity;
import com.example.Crud.repostiory.PersonRepository;
import org.reactivestreams.Publisher;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

public class MockPersonRepository implements PersonRepository {
    private final Map<UUID, PersonEntity> store = new HashMap<>();

    @Override
    public Mono<PersonEntity> save(PersonEntity entity) {
        store.put(entity.getId(), entity);
        return Mono.just(entity);
    }

    @Override
    public Mono<PersonEntity> findById(UUID id) {
        return Mono.justOrEmpty(store.get(id));
    }

    @Override
    public Mono<PersonEntity> findById(Publisher<UUID> id) {
        return null;
    }

    @Override
    public Mono<Boolean> existsById(UUID uuid) {
        return null;
    }

    @Override
    public Mono<Boolean> existsById(Publisher<UUID> id) {
        return null;
    }

    @Override
    public <S extends PersonEntity> Flux<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public <S extends PersonEntity> Flux<S> saveAll(Publisher<S> entityStream) {
        return null;
    }


    @Override
    public Flux<PersonEntity> findAll() {
        return Flux.fromIterable(store.values());
    }

    @Override
    public Flux<PersonEntity> findAllById(Iterable<UUID> uuids) {
        return null;
    }

    @Override
    public Flux<PersonEntity> findAllById(Publisher<UUID> idStream) {
        return null;
    }

    @Override
    public Mono<Long> count() {
        return null;
    }

    @Override
    public Mono<Void> deleteById(UUID uuid) {
        return null;
    }

    @Override
    public Mono<Void> deleteById(Publisher<UUID> id) {
        return null;
    }

    @Override
    public Mono<Void> delete(PersonEntity entity) {
        return null;
    }

    @Override
    public Mono<Void> deleteAllById(Iterable<? extends UUID> uuids) {
        return null;
    }

    @Override
    public Mono<Void> deleteAll(Iterable<? extends PersonEntity> entities) {
        return null;
    }

    @Override
    public Mono<Void> deleteAll(Publisher<? extends PersonEntity> entityStream) {
        return null;
    }

    @Override
    public Mono<Void> deleteAll() {
        return null;
    }

    @Override
    public <S extends PersonEntity> Mono<S> insert(S entity) {
        return null;
    }

    @Override
    public <S extends PersonEntity> Flux<S> insert(Iterable<S> entities) {
        return null;
    }

    @Override
    public <S extends PersonEntity> Flux<S> insert(Publisher<S> entities) {
        return null;
    }

    @Override
    public <S extends PersonEntity> Mono<S> findOne(Example<S> example) {
        return null;
    }

    @Override
    public <S extends PersonEntity> Flux<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends PersonEntity> Flux<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends PersonEntity> Mono<Long> count(Example<S> example) {
        return null;
    }

    @Override
    public <S extends PersonEntity> Mono<Boolean> exists(Example<S> example) {
        return null;
    }

    @Override
    public <S extends PersonEntity, R, P extends Publisher<R>> P findBy(Example<S> example, Function<FluentQuery.ReactiveFluentQuery<S>, P> queryFunction) {
        return null;
    }
    @Override
    public Flux<PersonEntity> findAll(Sort sort) {
        return null;
    }


}

