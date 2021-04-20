package io.ninjabet.football.service;

import io.ninjabet.auth.service.UserService;
import io.ninjabet.football.entity.Team;
import io.ninjabet.football.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamService extends DeleteManagerCrudService<Team, Long, TeamRepository> {

    @Autowired
    public TeamService(
            TeamRepository teamRepository,
            UserService userService
    ) {
        super(teamRepository, userService);
    }
}
