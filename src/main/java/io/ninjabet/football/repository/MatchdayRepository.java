package io.ninjabet.football.repository;

import io.ninjabet.football.entity.Competition;
import io.ninjabet.football.entity.Matchday;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchdayRepository extends CrudRepository<Matchday, Long> {
    Iterable<Matchday> findAllByDeletedFalse();

    Iterable<Matchday> findByCompetition(Competition competition);
}
