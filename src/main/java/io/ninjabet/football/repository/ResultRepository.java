package io.ninjabet.football.repository;

import io.ninjabet.core.repository.DeleteManagerRepository;
import io.ninjabet.football.entity.Result;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultRepository extends CrudRepository<Result, Long>, DeleteManagerRepository<Result> {
}
