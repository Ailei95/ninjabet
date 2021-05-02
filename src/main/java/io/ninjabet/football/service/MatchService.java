package io.ninjabet.football.service;

import io.ninjabet.core.service.CrudService;
import io.ninjabet.football.entity.Match;
import io.ninjabet.football.entity.Matchday;
import io.ninjabet.football.entity.Team;
import io.ninjabet.football.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MatchService extends CrudService<Match, Long, MatchRepository> {

    private final MatchdayService matchdayService;
    private final TeamService teamService;

    @Autowired
    public MatchService(
            MatchRepository matchRepository,
            MatchdayService matchdayService,
            TeamService teamService
    ) {
        super(matchRepository);
        this.matchdayService = matchdayService;
        this.teamService = teamService;
    }

    @Override
    public Match add(Match match) {
        if (match.getHome().getId() != null && match.getGuest().getId() != null && match.getMatchday().getId() != null) {
            Optional<Team> localHome = this.teamService.findById(match.getHome().getId());

            if (!localHome.isPresent()) { return null; }

            Optional<Team> localGuest = this.teamService.findById(match.getGuest().getId());

            if (!localGuest.isPresent()) { return null; }

            Optional<Matchday> localMatchday = this.matchdayService.findById(match.getMatchday().getId());

            if (!localMatchday.isPresent()) { return null; }

            match.setHome(localHome.get());

            match.setGuest(localGuest.get());

            match.setMatchday(localMatchday.get());

            return super.add(match);
        } else {
            return null;
        }
    }

    @Override
    public Optional<Match> update(Long id, Match match) {
        if (match.getHome().getId() != null && match.getGuest().getId() != null && match.getMatchday().getId() != null) {
            Optional<Team> localHome = this.teamService.findById(match.getHome().getId());

            if (!localHome.isPresent()) { return Optional.empty(); }

            Optional<Team> localGuest = this.teamService.findById(match.getGuest().getId());

            if (!localGuest.isPresent()) { return Optional.empty(); }

            Optional<Matchday> localMatchday = this.matchdayService.findById(match.getMatchday().getId());

            if (!localMatchday.isPresent()) { return Optional.empty(); }

            match.setHome(localHome.get());

            match.setGuest(localGuest.get());

            match.setMatchday(localMatchday.get());

            return super.update(id, match);
        } else {
            return Optional.empty();
        }
    }
}
