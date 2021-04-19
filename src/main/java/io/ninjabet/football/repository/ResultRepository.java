package io.ninjabet.football.repository;

import io.ninjabet.football.entity.Match;
import io.ninjabet.football.entity.Result;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultRepository extends CrudRepository<Result, Match> {
    Iterable<Result> findAllByDeletedFalse();
}
