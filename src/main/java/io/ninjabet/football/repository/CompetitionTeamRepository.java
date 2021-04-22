package io.ninjabet.football.repository;

import io.ninjabet.football.entity.CompetitionTeam;
import io.ninjabet.football.entity.CompetitionTeamKey;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompetitionTeamRepository extends CrudRepository<CompetitionTeam, CompetitionTeamKey> {
}
