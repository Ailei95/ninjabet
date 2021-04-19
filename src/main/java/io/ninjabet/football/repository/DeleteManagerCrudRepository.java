package io.ninjabet.football.repository;

import io.ninjabet.football.entity.DeleteManagerEntity;
import org.springframework.data.repository.CrudRepository;

public interface DeleteManagerCrudRepository<T extends DeleteManagerEntity, ID> extends CrudRepository<T, ID> {
    Iterable<T> findAllByDeletedFalse();
}
