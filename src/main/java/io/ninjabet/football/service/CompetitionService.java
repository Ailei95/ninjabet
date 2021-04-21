package io.ninjabet.football.service;

import io.ninjabet.auth.service.UserService;
import io.ninjabet.core.service.DeleteLoggerDMCrudService;
import io.ninjabet.football.entity.Competition;
import io.ninjabet.football.entity.Country;
import io.ninjabet.football.repository.CompetitionRepository;
import io.ninjabet.core.repository.ActionLoggerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Optional;

@Service
public class CompetitionService extends DeleteLoggerDMCrudService<Competition, Long, CompetitionRepository> {

    private final CountryService countryService;

    @Autowired
    public CompetitionService(
            ActionLoggerRepository actionLoggerRepository,
            CompetitionRepository competitionRepository,
            CountryService countryService,
            UserService userService
    ) {
        super(Competition.class, actionLoggerRepository, competitionRepository, userService);
        this.countryService = countryService;
    }

    public Iterable<Competition> findByCountry(Long countryId) {
        Optional<Country> localCountry = this.countryService.findById(countryId);

        if (!localCountry.isPresent()) { return new LinkedList<>(); }

        return this.crudRepository.findByCountry(localCountry.get());
    }
}
