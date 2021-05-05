package io.ninjabet.football.controller;

import io.ninjabet.football.entity.Competition;
import io.ninjabet.football.entity.dto.CompetitionDto;
import io.ninjabet.football.service.CompetitionService;
import io.ninjabet.football.service.CompetitionTeamService;
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
public class CompetitionController {

    private final CompetitionService competitionService;

    private final CompetitionTeamService competitionTeamService;

    private final ModelMapper modelMapper;

    Iterable<CompetitionDto> findAll() {
        return StreamSupport.stream(this.competitionService.findAll().spliterator(), false)
                .map(this::fromEntityToDto).collect(Collectors.toList());
    }

    @GetMapping(value = {"/competitions/{id}", "/admin/competitions/{id}"})
    CompetitionDto findById(@PathVariable Long id) {
        return fromEntityToDto(this.competitionService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Competition not found")));
    }

    @GetMapping(value = {"/competitions", "/admin/competitions"})
    Iterable<CompetitionDto> findBy(
            @RequestParam Optional<Long> countryId,
            @RequestParam Optional<Long> teamId
    ) {
        if (countryId.isPresent()) {
            return StreamSupport.stream(this.competitionService.findByCountryId(countryId.get()).spliterator(), false)
                    .map(this::fromEntityToDto).collect(Collectors.toList());
        }

        if (teamId.isPresent()) {
            return this.findAllByTeamId(teamId.get());
        }

        return this.findAll();
    }

    @GetMapping(value = {"/teams/{teamId}/competitions", "/admin/teams/{teamId}/competitions"})
    Iterable<CompetitionDto> findAllByTeamId(@PathVariable Long teamId) {
        return StreamSupport.stream(this.competitionTeamService.findByTeamId(teamId).spliterator(), false)
                .map((competitionTeam) -> fromEntityToDto(competitionTeam.getCompetition())).collect(Collectors.toList());
    }

    @PostMapping("/admin/competitions")
    CompetitionDto add(@RequestBody CompetitionDto competitionDto) {
        return fromEntityToDto(this.competitionService.add(fromDtoToEntity(competitionDto)));
    }

    @PutMapping("/admin/competitions/{id}")
    CompetitionDto update(@PathVariable Long id, @RequestBody CompetitionDto competitionDto) {
        return fromEntityToDto(this.competitionService.update(id, fromDtoToEntity(competitionDto))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Competition not found")));
    }

    @DeleteMapping("/admin/competitions/{id}")
    void delete(@PathVariable Long id) {
        if (!this.competitionService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Competition not found");
        }
    }

    private CompetitionDto fromEntityToDto(Competition competition) {
        return modelMapper.map(competition, CompetitionDto.class);
    }

    private Competition fromDtoToEntity(CompetitionDto countryDto) {
        return modelMapper.map(countryDto, Competition.class);
    }
}
