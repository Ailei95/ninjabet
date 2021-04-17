package io.ninjabet.football.controller;

import io.ninjabet.football.entity.JMatch;
import io.ninjabet.football.service.JMatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/matches")
public class JMatchController {

    private final JMatchService jMatchService;

    @Autowired
    public JMatchController(JMatchService jMatchService) { this.jMatchService = jMatchService; }

    @GetMapping("/")
    Iterable<JMatch> getMatches() {
        return this.jMatchService.getJMatches();
    }

    @GetMapping("/{id}")
    JMatch getMatchById(@PathVariable Long id) {
        return this.jMatchService.getJMatchById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Match not found"));
    }

    @PostMapping("/")
    JMatch addJMatch(@RequestBody JMatch jMatch) {
        return this.jMatchService.addJMatch(jMatch);
    }

    @PutMapping("/{id}")
    JMatch updateJMatch(@PathVariable Long id, @RequestBody JMatch jMatch) {
        return this.jMatchService.updateJMatch(id, jMatch).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Match not found"));
    }

    @DeleteMapping("/{id}")
    void deleteJMatch(@PathVariable Long id) {
        if (!this.jMatchService.deleteJMatch(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Match not found");
        }
    }
}
