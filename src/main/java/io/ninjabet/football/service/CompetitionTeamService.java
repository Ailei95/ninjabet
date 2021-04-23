package io.ninjabet.football.service;

import io.ninjabet.core.service.CrudService;
import io.ninjabet.football.entity.*;
import io.ninjabet.football.repository.CompetitionRepository;
import io.ninjabet.football.repository.CompetitionTeamRepository;
import io.ninjabet.football.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompetitionTeamService extends CrudService<CompetitionTeam, CompetitionTeamKey, CompetitionTeamRepository> {

    private final CompetitionRepository competitionRepository;
    private final TeamRepository teamRepository;

    @Autowired
    public CompetitionTeamService(
            CompetitionTeamRepository crudRepository,
            CompetitionRepository competitionRepository,
            TeamRepository teamRepository
    ) {
        super(crudRepository);

        this.competitionRepository = competitionRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    public Optional<CompetitionTeam> update(CompetitionTeamKey id, CompetitionTeam competitionTeam) {

        if (competitionTeam.competitionId != null && competitionTeam.teamId != null) {
            Optional<Competition> localCompetition = this.competitionRepository.findById(competitionTeam.competitionId);

            if (!localCompetition.isPresent()) { return Optional.empty(); }

            Optional<Team> localTeam = this.teamRepository.findById(competitionTeam.teamId);

            if (!localTeam.isPresent()) { return Optional.empty(); }

            competitionTeam.setCompetition(localCompetition.get());

            competitionTeam.setTeam(localTeam.get());

            competitionTeam.setId(new CompetitionTeamKey(competitionTeam.competitionId, competitionTeam.teamId));

            return super.update(id, competitionTeam);
        } else {
            return Optional.empty();
        }
    }
}
