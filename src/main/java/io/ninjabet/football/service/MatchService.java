package io.ninjabet.football.service;

import io.ninjabet.auth.service.UserService;
import io.ninjabet.core.service.DeleteLoggerDMCrudService;
import io.ninjabet.football.entity.Match;
import io.ninjabet.core.repository.ActionLoggerRepository;
import io.ninjabet.football.entity.Matchday;
import io.ninjabet.football.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MatchService extends DeleteLoggerDMCrudService<Match, Long, MatchRepository> {

    private final MatchdayService matchdayService;

    @Autowired
    public MatchService(
            ActionLoggerRepository actionLoggerRepository,
            MatchRepository matchRepository,
            MatchdayService matchdayService,
            UserService userService
    ) {
        super(Match.class, actionLoggerRepository, matchRepository, userService);
        this.matchdayService = matchdayService;
    }

    @Override
    public Optional<Match> update(Long id, Match match) {
        if (match.matchdayId != null) {
            Optional<Matchday> localMatchday = this.matchdayService.findById(match.matchdayId);

            if (!localMatchday.isPresent()) { return Optional.empty(); }

            match.setMatchday(localMatchday.get());
        }

        return super.update(id, match);
    }
}
