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
public class CompetitionService extends DeleteManagerCrudService<Competition, Long, CompetitionRepository> {

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

    public Iterable<Competition> getCompetitionsByCountry(Long countryId) {
        Optional<Country> localCountry = this.countryService.findById(countryId);

        if (!localCountry.isPresent()) { return new LinkedList<>(); }

        return this.crudRepository.findByCountry(localCountry.get());
    }
}
