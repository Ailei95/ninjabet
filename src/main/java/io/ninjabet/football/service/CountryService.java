package io.ninjabet.football.service;

import io.ninjabet.core.service.CrudService;
import io.ninjabet.football.entity.Country;
import io.ninjabet.football.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CountryService extends CrudService<Country, Long, CountryRepository> {

    @Autowired
    public CountryService(
            CountryRepository countryRepository
    ) {
        super(countryRepository);
    }
}
