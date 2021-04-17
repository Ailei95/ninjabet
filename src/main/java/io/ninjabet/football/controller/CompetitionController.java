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
@RequestMapping("/competitions")
public class CompetitionController {

    private final CompetitionService competitionService;

    @Autowired
    public CompetitionController(CompetitionService competitionService) { this.competitionService = competitionService; }

    @GetMapping("/")
    Iterable<Competition> getCompetitions() {
        return this.competitionService.getCompetitions();
    }

    @GetMapping("")
    Iterable<Competition> getCompetitionsByCountry(@RequestParam Optional<Long> countryId) {
        if (countryId.isPresent()) {
            return this.competitionService.getCompetitionsByCountry(countryId.get());
        }

        return new LinkedList<>();
    }

    @GetMapping("/{id}")
    Competition getCompetitionById(@PathVariable Long id) {
        return this.competitionService.getCompetitionById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Competition not found"));
    }

    @PostMapping("/")
    Competition addCompetition(@RequestBody Competition competition) {
        return this.competitionService.addCompetition(competition);
    }

    @PutMapping("/{id}")
    Competition updateCompetition(@PathVariable Long id, @RequestBody Competition competition) {
        return this.competitionService.updateCompetition(id, competition).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Competition not found"));
    }

    @DeleteMapping("/{id}")
    void deleteCompetition(@PathVariable Long id) {
        if(!this.competitionService.deleteCompetition(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Competition not found");
        }
    }
}
