package io.ninjabet.football.controller;

import io.ninjabet.football.entity.CompetitionTeam;
import io.ninjabet.football.entity.CompetitionTeamKey;
import io.ninjabet.football.entity.dto.CompetitionTeamDto;
import io.ninjabet.football.service.CompetitionTeamService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class CompetitionTeamController {

    private final CompetitionTeamService competitionTeamService;

    private final ModelMapper modelMapper;

    @GetMapping(value = {
            "/teams/{teamId}/competitions/{competitionId}",
            "/admin/teams/{teamId}/competitions/{competitionId}",
            "/competitions/{competitionId}/teams/{teamId}",
            "/admin/competitions/{competitionId}/teams/{teamId}"
    })
    CompetitionTeamDto findById(@PathVariable Long teamId, @PathVariable Long competitionId) {
        return fromEntityToDto(competitionTeamService.findById(new CompetitionTeamKey(competitionId, teamId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Element not found")));
    }

    @PostMapping(value = {
            "/admin/teams/{teamId}/competitions/{competitionId}",
            "/admin/competitions/{competitionId}/teams/{teamId}"
    })
    CompetitionTeamDto update(@PathVariable Long teamId, @PathVariable Long competitionId) {
        return fromEntityToDto(competitionTeamService.add(competitionId, teamId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Element not found")));
    }

    @DeleteMapping(value = {
            "/admin/teams/{teamId}/competitions/{competitionId}",
            "/admin/competitions/{competitionId}/teams/{teamId}"
    })
    void delete(@PathVariable Long teamId, @PathVariable Long competitionId) {
        if (!competitionTeamService.delete(new CompetitionTeamKey(competitionId, teamId))) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Element not found");
        }
    }

    private CompetitionTeamDto fromEntityToDto(CompetitionTeam competitionTeam) {
        return modelMapper.map(competitionTeam, CompetitionTeamDto.class);
    }

    private CompetitionTeam fromDtoToEntity(CompetitionTeamDto competitionTeamDto) {
        return modelMapper.map(competitionTeamDto, CompetitionTeam.class);
    }
}
