package io.ninjabet.football.controller;

import io.ninjabet.football.entity.Team;
import io.ninjabet.football.entity.dto.TeamDto;
import io.ninjabet.football.service.CompetitionTeamService;
import io.ninjabet.football.service.TeamService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class TeamController {

    private final TeamService teamService;

    private final CompetitionTeamService competitionTeamService;

    private final ModelMapper modelMapper;

    @GetMapping(value = {"/teams/", "/admin/teams/"})
    Iterable<TeamDto> findAll() {
        return StreamSupport.stream(this.teamService.findAll().spliterator(), false)
                .map(this::fromEntityToDto).collect(Collectors.toList());
    }

    @GetMapping(value = {"/teams/{id}", "/admin/teams/{id}"})
    TeamDto findById(@PathVariable Long id) {
        return fromEntityToDto(this.teamService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found")));
    }

    @GetMapping(value = {"/teams", "/admin/teams"})
    Iterable<TeamDto> findBy(@RequestParam Optional<Long> competitionId) {
        if (competitionId.isPresent()) {
            return this.findAllByCompetitionId(competitionId.get());
        }

        return this.findAll();
    }

    @GetMapping(value = {"/competition/{competitionId}/teams", "/admin/competition/{competitionId}/teams"})
    Iterable<TeamDto> findAllByCompetitionId(@PathVariable Long competitionId) {
        return StreamSupport.stream(this.competitionTeamService.findByCompetitionId(competitionId).spliterator(), false)
                .map((competitionTeam -> fromEntityToDto(competitionTeam.getTeam()))).collect(Collectors.toList());
    }

    @PostMapping("/admin/teams/")
    TeamDto add(@RequestBody TeamDto teamDto) {
        return fromEntityToDto(this.teamService.add(fromDtoToEntity(teamDto)));
    }

    @PutMapping("/admin/teams/{id}")
    TeamDto update(@PathVariable Long id, @RequestBody TeamDto teamDto) {
        return fromEntityToDto(this.teamService.update(id, fromDtoToEntity(teamDto))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found")));
    }

    @DeleteMapping("/admin/teams/{id}")
    void delete(@PathVariable Long id) {
        if (!this.teamService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found");
        }
    }

    private TeamDto fromEntityToDto(Team team) {
        return modelMapper.map(team, TeamDto.class);
    }

    private Team fromDtoToEntity(TeamDto teamDto) {
        return modelMapper.map(teamDto, Team.class);
    }
}
