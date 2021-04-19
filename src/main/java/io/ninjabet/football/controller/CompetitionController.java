package io.ninjabet.football.controller;

import io.ninjabet.football.entity.Competition;
import io.ninjabet.football.service.CompetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.LinkedList;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CompetitionController {

    private final CompetitionService competitionService;

    @Autowired
    public CompetitionController(CompetitionService competitionService) { this.competitionService = competitionService; }

    @GetMapping(value = {"/competitions/", "/admin/competitions/"})
    Iterable<Competition> getCompetitions() {
        return this.competitionService.getCompetitions();
    }

    @GetMapping(value = {"/competitions", "/admin/competitions"})
    Iterable<Competition> getCompetitionsByCountry(@RequestParam Optional<Long> countryId) {
        if (countryId.isPresent()) {
            return this.competitionService.getCompetitionsByCountry(countryId.get());
        }

        return new LinkedList<>();
    }

    @GetMapping(value = {"/competitions/{id}", "/admin/competitions/{id}"})
    Competition getCompetitionById(@PathVariable Long id) {
        return this.competitionService.getCompetitionById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Competition not found"));
    }

    @PostMapping("/admin/competitions/")
    Competition addCompetition(@RequestBody Competition competition) {
        return this.competitionService.addCompetition(competition);
    }

    @PutMapping("/admin/competitions/{id}")
    Competition updateCompetition(@PathVariable Long id, @RequestBody Competition competition) {
        return this.competitionService.updateCompetition(id, competition).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Competition not found"));
    }

    @DeleteMapping("/admin/competitions/{id}")
    void deleteCompetition(@PathVariable Long id) {
        if(!this.competitionService.deleteCompetition(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Competition not found");
        }
    }
}
