package io.ninjabet.football.service;

import io.ninjabet.football.entity.Competition;
import io.ninjabet.football.entity.MatchDay;
import io.ninjabet.football.repository.MatchDayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Optional;

@Service
public class MatchDayService {

    private final MatchDayRepository matchDayRepository;
    private final CompetitionService competitionService;

    @Autowired
    public MatchDayService(
            MatchDayRepository matchDayRepository,
            CompetitionService competitionService
    ) {
        this.matchDayRepository = matchDayRepository;
        this.competitionService = competitionService;
    }

    public Iterable<MatchDay> getMatchDays() {
        return this.matchDayRepository.findAll();
    }

    public Iterable<MatchDay> getMatchDaysByCompetition(Long competitionId) {
        Optional<Competition> competition = this.competitionService.getCompetitionById(competitionId);

        if (!competition.isPresent()) {
            new LinkedList<>();
        }

        return this.matchDayRepository.findByCompetition(competition.get());
    }

    public Optional<MatchDay> getMatchDayById(Long id) {
        return this.matchDayRepository.findById(id);
    }

    public MatchDay addMatchDay(MatchDay matchDay) {
        return this.matchDayRepository.save(matchDay);
    }

    public Optional<MatchDay> updateMatchDay(Long id, MatchDay matchDay) {
        Optional<MatchDay> localMatchDay = this.matchDayRepository.findById(id);

        if (!localMatchDay.isPresent()) {
            return Optional.empty();
        }

        localMatchDay.get().setName(matchDay.getName());
        localMatchDay.get().setCompetition(matchDay.getCompetition());
        localMatchDay.get().setFromDate(matchDay.getFromDate());
        localMatchDay.get().setToDate(matchDay.getToDate());

        return Optional.of(this.matchDayRepository.save(localMatchDay.get()));
    }

    public boolean deleteMatchDay(Long id) {
        Optional<MatchDay> matchDay = this.matchDayRepository.findById(id);

        if (!matchDay.isPresent()) {
            return false;
        }

        this.matchDayRepository.delete(matchDay.get());

        return true;
    }
}
