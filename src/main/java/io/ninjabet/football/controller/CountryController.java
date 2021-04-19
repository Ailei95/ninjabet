package io.ninjabet.football.controller;

import io.ninjabet.football.entity.Country;
import io.ninjabet.football.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")
public class CountryController {

    private final CountryService countryService;

    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping(value = {"/countries/", "admin/countries/"})
    Iterable<Country> getCountries() {
        return this.countryService.getCountries();
    }

    @GetMapping(value = {"/countries/{id}", "admin/countries/{id}"})
    Country getCountryById(@PathVariable Long id) {
        return this.countryService.getCountryById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Country not found"));
    }

    @PostMapping("/admin/countries/")
    Country addCountry(@RequestBody Country country) {
        return this.countryService.addCountry(country);
    }

    @PutMapping("/admin/countries/{id}")
    Country updateCountry(@PathVariable Long id, @RequestBody Country country) {
        return this.countryService.updateCountry(id, country).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Country not found"));
    }

    @DeleteMapping("/admin/countries/{id}")
    void deleteCountry(@PathVariable Long id) {
        if (!this.countryService.deleteCountry(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Country not found");
        }
    }
}
