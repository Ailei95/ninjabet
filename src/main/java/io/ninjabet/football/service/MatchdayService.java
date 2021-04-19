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
public class MatchdayService extends DeleteManagerService<Matchday, Long> {

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

    public Iterable<Matchday> getMatchdays() {
        return ((MatchdayRepository) this.crudRepository).findAllByDeletedFalse();
    }

    public Iterable<Matchday> getMatchdaysByCompetition(Long competitionId) {
        Optional<Competition> localCompetition = this.competitionService.getCompetitionById(competitionId);

        if (!localCompetition.isPresent()) {
            return new LinkedList<>();
        }

        return ((MatchdayRepository) this.crudRepository).findByCompetition(localCompetition.get());
    }

    public Optional<Matchday> getMatchdayById(Long id) {
        return this.crudRepository.findById(id);
    }

    public Matchday addMatchday(Matchday matchday) {
        return this.crudRepository.save(matchday);
    }

    public Optional<Matchday> updateMatchday(Long id, Matchday matchday) {
        Optional<Matchday> localMatchDay = this.crudRepository.findById(id);

        if (!localMatchDay.isPresent()) {
            return Optional.empty();
        }

        localMatchDay.get().setName(matchday.getName());
        localMatchDay.get().setCompetition(matchday.getCompetition());
        localMatchDay.get().setFromDate(matchday.getFromDate());
        localMatchDay.get().setToDate(matchday.getToDate());

        return Optional.of(this.crudRepository.save(localMatchDay.get()));
    }

    public boolean deleteMatchday(Long id) {
        return this.setEntityDeleted(id, true);
    }

    public boolean restoreMatchday(Long id) {
        return this.setEntityDeleted(id, false);
    }
}
