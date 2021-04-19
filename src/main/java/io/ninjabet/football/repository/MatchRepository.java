package io.ninjabet.football.repository;

import io.ninjabet.football.entity.Match;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends DeleteManagerCrudRepository<Match, Long> {
}
