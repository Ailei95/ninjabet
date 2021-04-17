package io.ninjabet.football.controller;

import io.ninjabet.football.entity.Team;
import io.ninjabet.football.service.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/teams")
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) { this.teamService = teamService; }

    @GetMapping("/")
    Iterable<Team> getTeams() {
        return this.teamService.getTeams();
    }

    @GetMapping("/{id}")
    Team getTeamById(@PathVariable Long id) {
        return this.teamService.getTeamById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found"));
    }

    @PostMapping("/")
    Team addTeam(@RequestBody Team team) {
        return this.teamService.addTeam(team);
    }
}
