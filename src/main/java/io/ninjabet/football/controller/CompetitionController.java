package io.ninjabet.football.controller;

import io.ninjabet.football.entity.Competition;
import io.ninjabet.football.service.CompetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CompetitionController {

    private final CompetitionService competitionService;

    @Autowired
    public CompetitionController(CompetitionService competitionService) { this.competitionService = competitionService; }

    @GetMapping(value = {"/competitions/", "/admin/competitions/"})
    Iterable<Competition> findAll() {
        return this.competitionService.findAll();
    }

    @GetMapping(value = {"/competitions", "/admin/competitions"})
    Iterable<Competition> findByCountry(@RequestParam Optional<Long> countryId) {
        if (countryId.isPresent()) { return this.competitionService.findByCountryId(countryId.get()); }

        return this.findAll();
    }

    @GetMapping(value = {"/competitions/{id}", "/admin/competitions/{id}"})
    Competition findById(@PathVariable Long id) {
        return this.competitionService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Competition not found"));
    }

    @PostMapping("/admin/competitions/")
    Competition add(@RequestBody Competition competition) {
        return this.competitionService.add(competition);
    }

    @PutMapping("/admin/competitions/{id}")
    Competition update(@PathVariable Long id, @RequestBody Competition competition) {
        return this.competitionService.update(id, competition).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Competition not found"));
    }

    @DeleteMapping("/admin/competitions/{id}")
    void delete(@PathVariable Long id) {
        if(!this.competitionService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Competition not found");
        }
    }
}
