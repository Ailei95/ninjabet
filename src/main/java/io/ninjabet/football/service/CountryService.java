package io.ninjabet.football.service;

import io.ninjabet.auth.service.UserService;
import io.ninjabet.football.entity.Country;
import io.ninjabet.football.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CountryService extends DeleteManagerService<Country, Long> {

    @Autowired
    public CountryService(
            CountryRepository countryRepository,
            UserService userService
    ) {
        super(countryRepository, userService);
    }

    public Iterable<Country> getCountries() {
        return ((CountryRepository) this.crudRepository).findAllByDeletedFalse();
    }

    public Optional<Country> getCountryById(Long id) {
        return this.crudRepository.findById(id);
    }

    public Country addCountry(Country country) { return this.crudRepository.save(country); }

    public Optional<Country> updateCountry(Long id, Country country) {
        Optional<Country> localCountry = this.crudRepository.findById(id);

        if (!localCountry.isPresent()) {
            return Optional.empty();
        }

        localCountry.get().setName(country.getName());
        localCountry.get().setIso2Code(country.getIso2Code());
        localCountry.get().setImageUrl(country.getImageUrl());

        return Optional.of(this.crudRepository.save(localCountry.get()));
    }

    public boolean deleteCountry(Long id) {
        return this.setEntityDeleted(id, true);
    }

    public boolean restoreCountry(Long id) {
        return this.setEntityDeleted(id, false);
    }
}
