package io.ninjabet.football.service;

import io.ninjabet.auth.service.UserService;
import io.ninjabet.core.service.DeleteLoggerDMCrudService;
import io.ninjabet.football.entity.Team;
import io.ninjabet.core.repository.ActionLoggerRepository;
import io.ninjabet.football.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamService extends DeleteLoggerDMCrudService<Team, Long, TeamRepository> {

    @Autowired
    public TeamService(
            ActionLoggerRepository actionLoggerRepository,
            TeamRepository teamRepository,
            UserService userService
    ) {
        super(Team.class, actionLoggerRepository, teamRepository, userService);
    }
}
