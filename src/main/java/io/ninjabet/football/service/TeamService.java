package io.ninjabet.football.service;

import io.ninjabet.football.entity.Team;
import io.ninjabet.football.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final CompetitionService competitionService;

    public TeamService(
            TeamRepository teamRepository,
            CompetitionService competitionService
    ) {
        this.teamRepository = teamRepository;
        this.competitionService = competitionService;
    }

    public Iterable<Team> getTeams() {
        return this.teamRepository.findAll();
    }

    public Optional<Team> getTeamById(Long id) {
        return this.teamRepository.findById(id);
    }

    public Team addTeam(Team team) {
        return this.teamRepository.save(team);
    }

    public Optional<Team> updateTeam(Long id, Team team) {
        Optional<Team> localTeam = this.teamRepository.findById(id);

        if (!localTeam.isPresent()) {
            return Optional.empty();
        }

        localTeam.get().setName(team.getName());
        localTeam.get().setImageUrl(team.getImageUrl());

        return Optional.of(this.teamRepository.save(localTeam.get()));
    }

    public boolean deleteTeam(Long id) {
        Optional<Team> localTeam = this.teamRepository.findById(id);

        if (!localTeam.isPresent()) {
            return false;
        }

        this.teamRepository.delete(localTeam.get());

        return true;
    }
}
