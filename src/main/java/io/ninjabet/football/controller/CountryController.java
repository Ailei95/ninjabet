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
    Iterable<Country> findAll() {
        return this.countryService.findAll();
    }

    @GetMapping(value = {"/countries/{id}", "admin/countries/{id}"})
    Country findById(@PathVariable Long id) {
        return this.countryService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Country not found"));
    }

    @PostMapping("/admin/countries/")
    Country add(@RequestBody Country country) {
        return this.countryService.add(country);
    }

    @PutMapping("/admin/countries/{id}")
    Country update(@PathVariable Long id, @RequestBody Country country) {
        return this.countryService.update(id, country).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Country not found"));
    }

    @DeleteMapping("/admin/countries/{id}")
    void delete(@PathVariable Long id) {
        if (!this.countryService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Country not found");
        }
    }
}
