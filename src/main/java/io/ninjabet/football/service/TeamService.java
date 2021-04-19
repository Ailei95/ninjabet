package io.ninjabet.football.service;

import io.ninjabet.auth.service.UserService;
import io.ninjabet.football.entity.Team;
import io.ninjabet.football.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeamService extends DeleteManagerService<Team, Long, TeamRepository> {

    @Autowired
    public TeamService(
            TeamRepository teamRepository,
            UserService userService
    ) {
        super(teamRepository, userService);
    }

    public Iterable<Team> getTeams() {
        return this.crudRepository.findAllByDeletedFalse();
    }

    public Optional<Team> getTeamById(Long id) {
        return this.crudRepository.findById(id);
    }

    public Team addTeam(Team team) {
        return this.crudRepository.save(team);
    }

    public Optional<Team> updateTeam(Long id, Team team) {
        Optional<Team> localTeam = this.crudRepository.findById(id);

        if (!localTeam.isPresent()) {
            return Optional.empty();
        }

        localTeam.get().setName(team.getName());
        localTeam.get().setImageUrl(team.getImageUrl());

        return Optional.of(this.crudRepository.save(localTeam.get()));
    }

    public boolean deleteTeam(Long id) {
        return this.setEntityDeleted(id, true);
    }

    public boolean restoreTeam(Long id) {
        return this.setEntityDeleted(id, false);
    }
}
