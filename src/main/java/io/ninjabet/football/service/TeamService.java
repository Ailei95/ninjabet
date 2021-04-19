package io.ninjabet.football.service;

import io.ninjabet.football.entity.Team;
import io.ninjabet.football.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class TeamService {

    private final TeamRepository teamRepository;

    public TeamService(
            TeamRepository teamRepository
    ) {
        this.teamRepository = teamRepository;
    }

    public Iterable<Team> getTeams() {
        return this.teamRepository.findAllByDeletedFalse();
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
        return this.setTeamIsDeleted(id, true);
    }

    public boolean restoreTeam(Long id) {
        return this.setTeamIsDeleted(id, false);
    }

    private boolean setTeamIsDeleted(Long id, boolean deleted) {
        Optional<Team> localTeam = this.teamRepository.findById(id);

        if (!localTeam.isPresent()) {
            return false;
        }

        if (deleted) {
            localTeam.get().setDeleteDate(new Date());
        } else {
            localTeam.get().setDeleteDate(null);
        }

        localTeam.get().setDeleted(deleted);

        this.teamRepository.save(localTeam.get());

        return true;
    }
}
