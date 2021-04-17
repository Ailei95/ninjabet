package io.ninjabet.football.service;

import io.ninjabet.football.entity.Competition;
import io.ninjabet.football.entity.Country;
import io.ninjabet.football.repository.CompetitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Optional;

@Service
public class CompetitionService {
    private final CompetitionRepository competitionRepository;
    private final CountryService countryService;

    @Autowired
    public CompetitionService(
            CompetitionRepository competitionRepository,
            CountryService countryService
    ) {
        this.competitionRepository = competitionRepository;
        this.countryService = countryService;
    }

    public Iterable<Competition> getCompetitions() {
        return this.competitionRepository.findAll();
    }

    public Optional<Competition> getCompetitionById(Long id) {
        return this.competitionRepository.findById(id);
    }

    public Iterable<Competition> getCompetitionsByCountry(Long countryId) {
        Optional<Country> country = this.countryService.getCountryById(countryId);

        if (!country.isPresent()) {
            return new LinkedList<>();
        }

        return this.competitionRepository.findByCountry(country.get());
    }

    public Competition addCompetition(Competition competition) {
        return this.competitionRepository.save(competition);
    }

    public Optional<Competition> updateCompetition(Long id, Competition competition) {
        Optional<Competition> localCompetition = this.competitionRepository.findById(id);

        if (!localCompetition.isPresent()) {
            return Optional.empty();
        }

        localCompetition.get().setName(competition.getName());
        localCompetition.get().setFromDate(competition.getFromDate());
        localCompetition.get().setToDate(competition.getToDate());
        localCompetition.get().setImageUrl(competition.getImageUrl());
        localCompetition.get().setCountry(competition.getCountry());
        localCompetition.get().setTeams(competition.getTeams());

        return Optional.of(this.competitionRepository.save(localCompetition.get()));
    }

    public boolean deleteCompetition(Long id) {
        Optional<Competition> competition = this.competitionRepository.findById(id);

        if (!competition.isPresent()) {
            return false;
        }

        this.competitionRepository.delete(competition.get());

        return true;
    }
}
