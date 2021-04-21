package io.ninjabet.football.service;

import io.ninjabet.auth.service.UserService;
import io.ninjabet.core.service.DeleteLoggerDMCrudService;
import io.ninjabet.football.entity.Competition;
import io.ninjabet.football.entity.Matchday;
import io.ninjabet.core.repository.ActionLoggerRepository;
import io.ninjabet.football.repository.MatchdayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Optional;

@Service
public class MatchdayService extends DeleteLoggerDMCrudService<Matchday, Long, MatchdayRepository> {

    private final CompetitionService competitionService;

    @Autowired
    public MatchdayService(
            ActionLoggerRepository actionLoggerRepository,
            MatchdayRepository matchdayRepository,
            CompetitionService competitionService,
            UserService userService
    ) {
        super(Matchday.class, actionLoggerRepository, matchdayRepository, userService);
        this.competitionService = competitionService;
    }

    public Iterable<Matchday> findByCompetition(Long competitionId) {
        Optional<Competition> localCompetition = this.competitionService.findById(competitionId);

        if (!localCompetition.isPresent()) { return new LinkedList<>(); }

        return this.crudRepository.findByCompetition(localCompetition.get());
    }
}
