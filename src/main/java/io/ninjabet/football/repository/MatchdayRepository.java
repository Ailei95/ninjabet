package io.ninjabet.football.repository;

import io.ninjabet.football.entity.Competition;
import io.ninjabet.football.entity.Matchday;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchdayRepository extends DeleteManagerCrudRepository<Matchday, Long> {
    Iterable<Matchday> findByCompetition(Competition competition);
}
