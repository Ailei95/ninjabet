package io.ninjabet.football.controller;

import io.ninjabet.football.entity.MatchDay;
import io.ninjabet.football.service.MatchDayService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/matchdays")
public class MatchDayController {

    private final MatchDayService matchDayService;
    public MatchDayController(MatchDayService matchDayService) { this.matchDayService = matchDayService; }

    @GetMapping("/")
    Iterable<MatchDay> getMatchDays() {
        return this.matchDayService.getMatchDays();
    }

    @GetMapping("/{id}")
    MatchDay getMatchDayById(@PathVariable Long id) {
        return this.matchDayService.getMatchDayById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "MatchDay Not found"));
    }

    @GetMapping("")
    Iterable<MatchDay> getMatchDaysByCompetition(@RequestParam Long competitionId) {
        return this.matchDayService.getMatchDaysByCompetition(competitionId);
    }

    @PostMapping("/")
    MatchDay addMatchDay(@RequestBody MatchDay matchDay) {
        return this.matchDayService.addMatchDay(matchDay);
    }

    @PutMapping("/{id}")
    MatchDay updateMatchDay(@PathVariable Long id, @RequestBody MatchDay matchDay) {
        return this.matchDayService.updateMatchDay(id, matchDay).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "MatchDay Not found"));
    }

    @DeleteMapping("/{id}")
    void deleteMatchDay(@PathVariable Long id) {
        if (!this.matchDayService.deleteMatchDay(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "MatchDay Not found");
        }
    }
}
