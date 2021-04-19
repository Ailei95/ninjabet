package io.ninjabet.football.service;

import io.ninjabet.football.entity.Country;
import io.ninjabet.football.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CountryService {

    private final CountryRepository countryRepository;

    @Autowired
    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public Iterable<Country> getCountries() {
        return this.countryRepository.findAll();
    }

    public Optional<Country> getCountryById(Long id) {
        return this.countryRepository.findById(id);
    }

    public Country addCountry(Country country) { return this.countryRepository.save(country); }

    public Optional<Country> updateCountry(Long id, Country country) {
        Optional<Country> localCountry = this.countryRepository.findById(id);

        if (!localCountry.isPresent()) {
            return Optional.empty();
        }

        localCountry.get().setName(country.getName());
        localCountry.get().setIso2Code(country.getIso2Code());
        localCountry.get().setImageUrl(country.getImageUrl());

        return Optional.of(this.countryRepository.save(localCountry.get()));
    }

    public boolean deleteCountry(Long id) {
        Optional<Country> localCountry = this.countryRepository.findById(id);

        if (!localCountry.isPresent()) {
            return false;
        }

        this.countryRepository.delete(localCountry.get());

        return true;
    }
}
