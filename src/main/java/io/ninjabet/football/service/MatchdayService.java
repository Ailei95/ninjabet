package io.ninjabet.football.service;

import io.ninjabet.auth.service.UserService;
import io.ninjabet.football.entity.Competition;
import io.ninjabet.football.entity.Matchday;
import io.ninjabet.football.repository.MatchdayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Optional;

@Service
public class MatchdayService extends DeleteManagerCrudService<Matchday, Long, MatchdayRepository> {

    private final CompetitionService competitionService;

    @Autowired
    public MatchdayService(
            MatchdayRepository matchdayRepository,
            CompetitionService competitionService,
            UserService userService
    ) {
        super(matchdayRepository, userService);
        this.competitionService = competitionService;
    }

    public Iterable<Matchday> getMatchdaysByCompetition(Long competitionId) {
        Optional<Competition> localCompetition = this.competitionService.findById(competitionId);

        if (!localCompetition.isPresent()) { return new LinkedList<>(); }

        return this.crudRepository.findByCompetition(localCompetition.get());
    }
}
