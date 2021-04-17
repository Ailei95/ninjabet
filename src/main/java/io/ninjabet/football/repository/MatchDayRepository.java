package io.ninjabet.football.repository;

import io.ninjabet.football.entity.Competition;
import io.ninjabet.football.entity.MatchDay;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchDayRepository extends CrudRepository<MatchDay, Long> {
    Iterable<MatchDay> findByCompetition(Competition competition);
}
