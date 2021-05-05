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

    public Iterable<CompetitionTeam> findByCompetitionId(Long competitionId) {
        return crudRepository.findByCompetition_Id(competitionId);
    }

    public Iterable<CompetitionTeam> findByTeamId(Long teamId) {
        return crudRepository.findByTeam_Id(teamId);
    }

    public Optional<CompetitionTeam> add(Long competitionId, Long teamId) {
        Optional<CompetitionTeam> competitionTeamOptional = findById(new CompetitionTeamKey(competitionId, teamId));

        if (competitionTeamOptional.isPresent()) { return Optional.empty(); }

        CompetitionTeam competitionTeam = new CompetitionTeam();

        competitionTeam.setId(new CompetitionTeamKey(competitionId, teamId));

        competitionRepository.findById(competitionId).ifPresent(competitionTeam::setCompetition);

        teamRepository.findById(teamId).ifPresent(competitionTeam::setTeam);

        return Optional.of(add(competitionTeam));
    }

    @Deprecated
    @Override
    public Optional<CompetitionTeam> update(CompetitionTeamKey competitionTeamKey, CompetitionTeam competitionTeam) {
        throw new UnsupportedOperationException("The method cannot be called");
    }
}
