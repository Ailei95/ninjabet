package io.ninjabet.football.service;

import io.ninjabet.football.entity.Competition;
import io.ninjabet.football.entity.Matchday;
import io.ninjabet.football.repository.MatchdayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedList;
import java.util.Optional;

@Service
public class MatchdayService {

    private final MatchdayRepository matchdayRepository;
    private final CompetitionService competitionService;

    @Autowired
    public MatchdayService(
            MatchdayRepository matchdayRepository,
            CompetitionService competitionService
    ) {
        this.matchdayRepository = matchdayRepository;
        this.competitionService = competitionService;
    }

    public Iterable<Matchday> getMatchdays() {
        return this.matchdayRepository.findAllByDeletedFalse();
    }

    public Iterable<Matchday> getMatchdaysByCompetition(Long competitionId) {
        Optional<Competition> localCompetition = this.competitionService.getCompetitionById(competitionId);

        if (!localCompetition.isPresent()) {
            return new LinkedList<>();
        }

        return this.matchdayRepository.findByCompetition(localCompetition.get());
    }

    public Optional<Matchday> getMatchdayById(Long id) {
        return this.matchdayRepository.findById(id);
    }

    public Matchday addMatchday(Matchday matchday) {
        return this.matchdayRepository.save(matchday);
    }

    public Optional<Matchday> updateMatchday(Long id, Matchday matchday) {
        Optional<Matchday> localMatchDay = this.matchdayRepository.findById(id);

        if (!localMatchDay.isPresent()) {
            return Optional.empty();
        }

        localMatchDay.get().setName(matchday.getName());
        localMatchDay.get().setCompetition(matchday.getCompetition());
        localMatchDay.get().setFromDate(matchday.getFromDate());
        localMatchDay.get().setToDate(matchday.getToDate());

        return Optional.of(this.matchdayRepository.save(localMatchDay.get()));
    }

    public boolean deleteMatchday(Long id) {
        return this.setMatchdayIsDeleted(id, true);
    }

    public boolean restoreMatchday(Long id) {
        return this.setMatchdayIsDeleted(id, false);
    }

    private boolean setMatchdayIsDeleted(Long id, boolean deleted) {
        Optional<Matchday> localMatchday = this.matchdayRepository.findById(id);

        if (!localMatchday.isPresent()) {
            return false;
        }

        if (deleted) {
            localMatchday.get().setDeleteDate(new Date());
        } else {
            localMatchday.get().setDeleteDate(null);
        }

        localMatchday.get().setDeleted(deleted);

        this.matchdayRepository.save(localMatchday.get());

        return true;
    }
}
