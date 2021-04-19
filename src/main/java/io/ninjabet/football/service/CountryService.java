package io.ninjabet.football.service;

import io.ninjabet.auth.entity.User;
import io.ninjabet.auth.service.UserService;
import io.ninjabet.football.entity.Country;
import io.ninjabet.football.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class CountryService {

    private final CountryRepository countryRepository;

    private final UserService userService;

    @Autowired
    public CountryService(
            CountryRepository countryRepository,
            UserService userService
    ) {
        this.countryRepository = countryRepository;
        this.userService = userService;
    }

    public Iterable<Country> getCountries() {
        return this.countryRepository.findAllByDeletedFalse();
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
        return this.setCountryIsDeleted(id, true);
    }

    public boolean restoreCountry(Long id) {
        return this.setCountryIsDeleted(id, false);
    }

    private boolean setCountryIsDeleted(Long id, boolean deleted) {
        Optional<Country> localCountry = this.countryRepository.findById(id);

        if (!localCountry.isPresent()) {
            return false;
        }

        if (deleted) {
            localCountry.get().setDeleteDate(new Date());
        } else {
            localCountry.get().setDeleteDate(null);
        }

        // Recupera i dati dell'utente loggato in questo momento

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        Optional<User> user = userService.getUserByEmail(username);

        if (user.isPresent()) {
            localCountry.get().setLastDeleteActionUser(user.get());
        }

        localCountry.get().setDeleted(deleted);

        this.countryRepository.save(localCountry.get());

        return true;
    }
}
