package io.ninjabet.football.service;

import io.ninjabet.core.service.CrudService;
import io.ninjabet.football.entity.CompetitionTeam;
import io.ninjabet.football.entity.CompetitionTeamKey;
import io.ninjabet.football.repository.CompetitionTeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompetitionTeamService extends CrudService<CompetitionTeam, CompetitionTeamKey, CompetitionTeamRepository> {

    @Autowired
    public CompetitionTeamService(
            CompetitionTeamRepository crudRepository
    ) {
        super(crudRepository);
    }
}
