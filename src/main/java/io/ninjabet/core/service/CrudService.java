package io.ninjabet.core.service;

import io.ninjabet.core.entity.AbstractEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public abstract class CrudService<T extends AbstractEntity<ID>, ID,  R extends CrudRepository<T, ID>> {

    protected final R crudRepository;

    public CrudService(R crudRepository) { this.crudRepository = crudRepository; }

    public Iterable<T> findAll() { return this.crudRepository.findAll(); }

    public Optional<T> findById(ID id) { return this.crudRepository.findById(id); }

    public T add(T t) {
        return this.crudRepository.save(t);
    }

    public Optional<T> update(ID id, T t) {
        Optional<T> local = this.crudRepository.findById(id);

        if (!local.isPresent() || !t.getId().equals(id)) { return Optional.empty(); }

        return Optional.of(this.crudRepository.save(t));
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean delete(ID id) {
        Optional<T> local = this.crudRepository.findById(id);

        if (!local.isPresent()) { return false; }

        this.crudRepository.delete(local.get());

        return true;
    }
}
