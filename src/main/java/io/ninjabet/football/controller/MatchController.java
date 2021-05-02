package io.ninjabet.football.controller;

import io.ninjabet.football.entity.Match;
import io.ninjabet.football.entity.dto.MatchDto;
import io.ninjabet.football.service.MatchService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class MatchController {

    private final MatchService matchService;

    private final ModelMapper modelMapper;

    @GetMapping(value = {"/matches/", "/admin/matches/"})
    Iterable<MatchDto> findAll() {
        return StreamSupport.stream(this.matchService.findAll().spliterator(), false)
                .map(this::fromEntityToDto).collect(Collectors.toList());
    }

    @GetMapping(value = {"/matches/{id}", "/admin/matches/{id}"})
    MatchDto findById(@PathVariable Long id) {
        return fromEntityToDto(this.matchService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Match not found")));
    }

    @PostMapping("/admin/matches/")
    MatchDto add(@RequestBody MatchDto matchDto) {
        return fromEntityToDto(this.matchService.add(fromDtoToEntity(matchDto)));
    }

    @PutMapping("/admin/matches/{id}")
    MatchDto update(@PathVariable Long id, @RequestBody MatchDto matchDto) {
        return fromEntityToDto(this.matchService.update(id, fromDtoToEntity(matchDto))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Match not found")));
    }

    @DeleteMapping("/admin/matches/{id}")
    void delete(@PathVariable Long id) {
        if (!this.matchService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Match not found");
        }
    }

    private MatchDto fromEntityToDto(Match match) {
        return modelMapper.map(match, MatchDto.class);
    }

    private Match fromDtoToEntity(MatchDto matchDto) {
        return modelMapper.map(matchDto, Match.class);
    }
}
