package io.ninjabet.football.service;

import io.ninjabet.auth.service.UserService;
import io.ninjabet.core.service.DeleteLoggerDMCrudService;
import io.ninjabet.football.entity.Country;
import io.ninjabet.football.repository.CountryRepository;
import io.ninjabet.core.repository.ActionLoggerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CountryService extends DeleteLoggerDMCrudService<Country, Long, CountryRepository> {

    @Autowired
    public CountryService(
            ActionLoggerRepository actionLoggerRepository,
            CountryRepository countryRepository,
            UserService userService
    ) {
        super(Country.class, actionLoggerRepository, countryRepository, userService);
    }
}
