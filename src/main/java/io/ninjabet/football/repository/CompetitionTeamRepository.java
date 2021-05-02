package io.ninjabet.football.repository;

import io.ninjabet.football.entity.CompetitionTeam;
import io.ninjabet.football.entity.CompetitionTeamKey;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CompetitionTeamRepository extends CrudRepository<CompetitionTeam, CompetitionTeamKey> {
    Iterable<CompetitionTeam> findByCompetition_Id(@Param("competitionId") Long competitionId);

    Iterable<CompetitionTeam> findByTeam_Id(@Param("teamId") Long teamId);
}
