package io.ninjabet.football.service;

import io.ninjabet.core.service.CrudService;
import io.ninjabet.football.entity.Competition;
import io.ninjabet.football.entity.Matchday;
import io.ninjabet.football.repository.MatchdayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MatchdayService extends CrudService<Matchday, Long, MatchdayRepository> {

    private final CompetitionService competitionService;

    @Autowired
    public MatchdayService(
            MatchdayRepository matchdayRepository,
            CompetitionService competitionService
    ) {
        super(matchdayRepository);
        this.competitionService = competitionService;
    }

    public Iterable<Matchday> findByCompetition(Long competitionId) {
        return this.crudRepository.findByCompetition_Id(competitionId);
    }

    @Override
    public Matchday add(Matchday matchday) {
        if (matchday.competitionId != null) {
            Optional<Competition> localCompetition = this.competitionService.findById(matchday.competitionId);

            if (!localCompetition.isPresent()) { return null; }

            matchday.setCompetition(localCompetition.get());

            return super.add(matchday);
        } else {
            return null;
        }
    }

    @Override
    public Optional<Matchday> update(Long id, Matchday matchday) {
        if (matchday.competitionId != null) {
            Optional<Competition> localCompetition = this.competitionService.findById(matchday.competitionId);

            if (!localCompetition.isPresent()) { return Optional.empty(); }

            matchday.setCompetition(localCompetition.get());

            return super.update(id, matchday);
        } else {
            return Optional.empty();
        }
    }
}
