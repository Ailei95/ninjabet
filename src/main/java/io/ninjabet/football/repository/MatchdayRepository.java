package io.ninjabet.football.repository;

import io.ninjabet.football.entity.Matchday;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchdayRepository extends CrudRepository<Matchday, Long> {
    Iterable<Matchday> findByCompetition_Id(@Param("competitionId") Long competitionId);
}
