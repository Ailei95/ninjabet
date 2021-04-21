package io.ninjabet.football.repository;

import io.ninjabet.core.repository.DeleteManagerRepository;
import io.ninjabet.football.entity.Match;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends CrudRepository<Match, Long>, DeleteManagerRepository<Match> {
}
