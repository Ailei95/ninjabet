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

    @Override
    public Optional<CompetitionTeam> update(CompetitionTeamKey id, CompetitionTeam competitionTeam) {

        if (competitionTeam.getCompetition().getId() != null && competitionTeam.getTeam().getId() != null) {
            Optional<Competition> localCompetition = this.competitionRepository.findById(competitionTeam.getCompetition().getId());

            if (!localCompetition.isPresent()) { return Optional.empty(); }

            Optional<Team> localTeam = this.teamRepository.findById(competitionTeam.getTeam().getId());

            if (!localTeam.isPresent()) { return Optional.empty(); }

            competitionTeam.setCompetition(localCompetition.get());

            competitionTeam.setTeam(localTeam.get());

            competitionTeam.setId(new CompetitionTeamKey(competitionTeam.getCompetition().getId(), competitionTeam.getTeam().getId()));

            return super.update(id, competitionTeam);
        } else {
            return Optional.empty();
        }
    }
}
