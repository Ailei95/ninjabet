package io.ninjabet.football.service;

import io.ninjabet.auth.service.UserService;
import io.ninjabet.football.entity.AbstractEntity;
import io.ninjabet.football.entity.DeleteManagerEntity;
import io.ninjabet.football.repository.DeleteManagerRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public abstract class CrudService<T extends DeleteManagerEntity & AbstractEntity<ID>, ID,  R extends CrudRepository<T, ID> & DeleteManagerRepository<T>>
        extends DeleteManagerService<T, ID, R>{
    public CrudService(R crudRepository, UserService userService) {
        super(crudRepository, userService);
    }

    public Iterable<T> findAll() { return this.crudRepository.findAllByDeletedFalse(); }

    public Optional<T> findById(ID id) { return this.crudRepository.findById(id); }

    public T add(T t) {
        return this.crudRepository.save(t);
    }

    public Optional<T> update(ID id, T t) {
        Optional<T> local = this.crudRepository.findById(id);

        if (!local.isPresent() || !t.getId().equals(id)) { return Optional.empty(); }

        return Optional.of(this.crudRepository.save(t));
    }

    public boolean delete(ID id) {
        return this.setEntityDeleted(id, true);
    }

    public boolean restore(ID id) {
        return this.setEntityDeleted(id, false);
    }
}
