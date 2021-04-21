package io.ninjabet.core.repository;

import io.ninjabet.core.entity.DeleteManagerEntity;

import java.util.Date;

public interface DeleteManagerRepository<T extends DeleteManagerEntity> {
    Iterable<T> findAllByDeleteDateEquals(Date date);

//    T findByIdAndDeleteDateEquals()
}
