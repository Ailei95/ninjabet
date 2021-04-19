package io.ninjabet.football.service;

import io.ninjabet.football.entity.Competition;
import io.ninjabet.football.entity.Country;
import io.ninjabet.football.repository.CompetitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
        return this.competitionRepository.findAllByDeletedFalse();
    }

    public Optional<Competition> getCompetitionById(Long id) {
        return this.competitionRepository.findById(id);
    }

    public Iterable<Competition> getCompetitionsByCountry(Long countryId) {
        Optional<Country> localCountry = this.countryService.getCountryById(countryId);

        if (!localCountry.isPresent()) {
            return new LinkedList<>();
        }

        return this.competitionRepository.findByCountry(localCountry.get());
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
        return this.setCompetitionIsDeleted(id, true);
    }

    public boolean restoreCompetition(Long id) {
        return this.setCompetitionIsDeleted(id, false);
    }

    private boolean setCompetitionIsDeleted(Long id, boolean deleted) {
        Optional<Competition> localCompetition = this.competitionRepository.findById(id);

        if (!localCompetition.isPresent()) {
            return false;
        }

        if (deleted) {
            localCompetition.get().setDeleteDate(new Date());
        } else {
            localCompetition.get().setDeleteDate(null);
        }

        localCompetition.get().setDeleted(deleted);

        this.competitionRepository.save(localCompetition.get());

        return true;
    }
}
