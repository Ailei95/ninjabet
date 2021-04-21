package io.ninjabet.core.repository;

import io.ninjabet.core.entity.DeleteManagerEntity;

public interface DeleteManagerRepository<T extends DeleteManagerEntity> {
    Iterable<T> findAllByDeleteDateIsNull();
}
