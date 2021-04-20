package io.ninjabet.football.controller;

import io.ninjabet.football.entity.Matchday;
import io.ninjabet.football.service.MatchdayService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")
public class MatchdayController {

    private final MatchdayService matchdayService;
    public MatchdayController(MatchdayService matchDayService) { this.matchdayService = matchDayService; }

    @GetMapping(value = {"/matchdays/", "/admin/matchdays/"})
    Iterable<Matchday> findAll() {
        return this.matchdayService.findAll();
    }

    @GetMapping(value = {"/matchdays/{id}", "/admin/matchdays/{id}"})
    Matchday findById(@PathVariable Long id) {
        return this.matchdayService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "MatchDay Not found"));
    }

    @GetMapping(value = {"/matchdays", "/admin/matchdays"})
    Iterable<Matchday> getMatchdaysByCompetition(@RequestParam Long competitionId) {
        return this.matchdayService.getMatchdaysByCompetition(competitionId);
    }

    @PostMapping("/admin/matchdays/")
    Matchday add(@RequestBody Matchday matchday) {
        return this.matchdayService.add(matchday);
    }

    @PutMapping("/admin/matchdays/{id}")
    Matchday update(@PathVariable Long id, @RequestBody Matchday matchday) {
        return this.matchdayService.update(id, matchday).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "MatchDay Not found"));
    }

    @DeleteMapping("/admin/matchdays/{id}")
    void delete(@PathVariable Long id) {
        if (!this.matchdayService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "MatchDay Not found");
        }
    }
}
