package io.ninjabet.football.service;

import io.ninjabet.core.service.CrudService;
import io.ninjabet.football.entity.*;
import io.ninjabet.football.repository.CompetitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompetitionService extends CrudService<Competition, Long, CompetitionRepository> {

    private final CountryService countryService;

    @Autowired
    public CompetitionService(
            CompetitionRepository competitionRepository,
            CountryService countryService
    ) {
        super(competitionRepository);

        this.countryService = countryService;
    }

    public Iterable<Competition> findByCountryId(Long countryId) {
        return this.crudRepository.findByCountry_Id(countryId);
    }

    @Override
    public Competition add(Competition competition) {
        if (competition.countryId != null) {
            Optional<Country> localCountry = this.countryService.findById(competition.countryId);

            if (!localCountry.isPresent()) { return null; }

            competition.setCountry(localCountry.get());

            return super.add(competition);
        } else {
            return null;
        }
    }

    @Override
    public Optional<Competition> update(Long id, Competition competition) {
        if (competition.countryId != null) {
            Optional<Country> localCountry = this.countryService.findById(competition.countryId);

            if (!localCountry.isPresent()) { return Optional.empty(); }

            competition.setCountry(localCountry.get());

            return super.update(id, competition);
        } else {
            return Optional.empty();
        }
    }
}
