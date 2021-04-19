package io.ninjabet.football.service;

import io.ninjabet.auth.service.UserService;
import io.ninjabet.football.entity.Competition;
import io.ninjabet.football.entity.Country;
import io.ninjabet.football.repository.CompetitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Optional;

@Service
public class CompetitionService extends DeleteManagerService<Competition, Long, CompetitionRepository> {

    private final CountryService countryService;

    @Autowired
    public CompetitionService(
            CompetitionRepository competitionRepository,
            CountryService countryService,
            UserService userService
    ) {
        super(competitionRepository, userService);
        this.countryService = countryService;
    }

    public Iterable<Competition> getCompetitions() {
        return this.crudRepository.findAllByDeletedFalse();
    }

    public Optional<Competition> getCompetitionById(Long id) {
        return this.crudRepository.findById(id);
    }

    public Iterable<Competition> getCompetitionsByCountry(Long countryId) {
        Optional<Country> localCountry = this.countryService.getCountryById(countryId);

        if (!localCountry.isPresent()) {
            return new LinkedList<>();
        }

        return this.crudRepository.findByCountry(localCountry.get());
    }

    public Competition addCompetition(Competition competition) {
        return this.crudRepository.save(competition);
    }

    public Optional<Competition> updateCompetition(Long id, Competition competition) {
        Optional<Competition> localCompetition = this.crudRepository.findById(id);

        if (!localCompetition.isPresent()) {
            return Optional.empty();
        }

        localCompetition.get().setName(competition.getName());
        localCompetition.get().setFromDate(competition.getFromDate());
        localCompetition.get().setToDate(competition.getToDate());
        localCompetition.get().setImageUrl(competition.getImageUrl());
        localCompetition.get().setCountry(competition.getCountry());
        localCompetition.get().setTeams(competition.getTeams());

        return Optional.of(this.crudRepository.save(localCompetition.get()));
    }

    public boolean deleteCompetition(Long id) {
        return this.setEntityDeleted(id, true);
    }

    public boolean restoreCompetition(Long id) {
        return this.setEntityDeleted(id, false);
    }
}
