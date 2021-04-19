package io.ninjabet.football.repository;

import io.ninjabet.football.entity.Match;
import io.ninjabet.football.entity.Result;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultRepository extends DeleteManagerCrudRepository<Result, Match> {
}
