package io.ninjabet.football.repository;

import io.ninjabet.football.entity.DeleteManagerEntity;

public interface DeleteManagerRepository<T extends DeleteManagerEntity> {
    Iterable<T> findAllByDeletedFalse();
}
