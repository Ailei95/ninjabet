package io.ninjabet.football.controller;

import io.ninjabet.football.entity.Match;
import io.ninjabet.football.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")
public class MatchController {

    private final MatchService matchService;

    @Autowired
    public MatchController(MatchService matchService) { this.matchService = matchService; }

    @GetMapping(value = {"/matches/", "/admin/matches/"})
    Iterable<Match> findAll() {
        return this.matchService.findAll();
    }

    @GetMapping(value = {"/matches/{id}", "/admin/matches/{id}"})
    Match findById(@PathVariable Long id) {
        return this.matchService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Match not found"));
    }

    @PostMapping("/admin/matches/")
    Match add(@RequestBody Match match) {
        return this.matchService.add(match);
    }

    @PutMapping("/admin/matches/{id}")
    Match update(@PathVariable Long id, @RequestBody Match match) {
        return this.matchService.update(id, match).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Match not found"));
    }

    @DeleteMapping("/admin/matches/{id}")
    void delete(@PathVariable Long id) {
        if (!this.matchService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Match not found");
        }
    }
}
