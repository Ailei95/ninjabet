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
    Iterable<Team> getTeams() {
        return this.teamService.getTeams();
    }

    @GetMapping(value = {"/teams/{id}", "/admin/teams/{id}"})
    Team getTeamById(@PathVariable Long id) {
        return this.teamService.getTeamById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found"));
    }

    @PostMapping("/admin/teams/")
    Team addTeam(@RequestBody Team team) {
        return this.teamService.addTeam(team);
    }

    @PutMapping("/admin/teams/{id}")
    Team updateTeam(@PathVariable Long id, @RequestBody Team team) {
        return this.teamService.updateTeam(id, team).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found"));
    }

    @DeleteMapping("/admin/teams/{id}")
    void deleteTeam(@PathVariable Long id) {
        if (!this.teamService.deleteTeam(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found");
        }
    }
}
