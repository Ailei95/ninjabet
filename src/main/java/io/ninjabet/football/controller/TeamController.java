package io.ninjabet.football.controller;

import io.ninjabet.football.entity.Team;
import io.ninjabet.football.service.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) { this.teamService = teamService; }

    @GetMapping(value = {"/teams/", "/admin/teams/"})
    Iterable<Team> findAll() {
        return this.teamService.findAll();
    }

    @GetMapping(value = {"/teams/{id}", "/admin/teams/{id}"})
    Team findById(@PathVariable Long id) {
        return this.teamService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found"));
    }

    @PostMapping("/admin/teams/")
    Team add(@RequestBody Team team) {
        return this.teamService.add(team);
    }

    @PutMapping("/admin/teams/{id}")
    Team update(@PathVariable Long id, @RequestBody Team team) {
        return this.teamService.update(id, team).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found"));
    }

    @DeleteMapping("/admin/teams/{id}")
    void delete(@PathVariable Long id) {
        if (!this.teamService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found");
        }
    }
}
