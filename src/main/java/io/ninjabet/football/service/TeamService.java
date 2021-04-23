package io.ninjabet.football.service;

import io.ninjabet.core.service.CrudService;
import io.ninjabet.football.entity.Team;
import io.ninjabet.football.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamService extends CrudService<Team, Long, TeamRepository> {

    @Autowired
    public TeamService(
            TeamRepository teamRepository
    ) {
        super(teamRepository);
    }
}
