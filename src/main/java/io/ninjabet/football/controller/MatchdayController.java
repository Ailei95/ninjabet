package io.ninjabet.football.controller;

import io.ninjabet.football.entity.Matchday;
import io.ninjabet.football.service.MatchdayService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/matchdays")
public class MatchdayController {

    private final MatchdayService matchdayService;
    public MatchdayController(MatchdayService matchDayService) { this.matchdayService = matchDayService; }

    @GetMapping("/")
    Iterable<Matchday> getMatchdays() {
        return this.matchdayService.getMatchdays();
    }

    @GetMapping("/{id}")
    Matchday getMatchDayById(@PathVariable Long id) {
        return this.matchdayService.getMatchdayById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "MatchDay Not found"));
    }

    @GetMapping("")
    Iterable<Matchday> getMatchdaysByCompetition(@RequestParam Long competitionId) {
        return this.matchdayService.getMatchdaysByCompetition(competitionId);
    }

    @PostMapping("/")
    Matchday addMatchday(@RequestBody Matchday matchday) {
        return this.matchdayService.addMatchday(matchday);
    }

    @PutMapping("/{id}")
    Matchday updateMatchday(@PathVariable Long id, @RequestBody Matchday matchday) {
        return this.matchdayService.updateMatchday(id, matchday).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "MatchDay Not found"));
    }

    @DeleteMapping("/{id}")
    void deleteMatchday(@PathVariable Long id) {
        if (!this.matchdayService.deleteMatchday(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "MatchDay Not found");
        }
    }
}
