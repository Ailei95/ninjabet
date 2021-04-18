package io.ninjabet.football.controller;

import io.ninjabet.football.entity.Match;
import io.ninjabet.football.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/matches")
public class MatchController {

    private final MatchService matchService;

    @Autowired
    public MatchController(MatchService matchService) { this.matchService = matchService; }

    @GetMapping("/")
    Iterable<Match> getMatches() {
        return this.matchService.getMatches();
    }

    @GetMapping("/{id}")
    Match getMatchById(@PathVariable Long id) {
        return this.matchService.getMatchById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Match not found"));
    }

    @PostMapping("/")
    Match addMatch(@RequestBody Match match) {
        return this.matchService.addMatch(match);
    }

    @PutMapping("/{id}")
    Match updateMatch(@PathVariable Long id, @RequestBody Match match) {
        return this.matchService.updateMatch(id, match).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Match not found"));
    }

    @DeleteMapping("/{id}")
    void deleteMatch(@PathVariable Long id) {
        if (!this.matchService.deleteMatch(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Match not found");
        }
    }
}
