package io.ninjabet.football.controller;

import io.ninjabet.football.entity.Matchday;
import io.ninjabet.football.entity.dto.MatchdayDto;
import io.ninjabet.football.service.MatchdayService;
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
public class MatchdayController {

    private final MatchdayService matchdayService;

    private final ModelMapper modelMapper;

    @GetMapping(value = {"/matchdays/", "/admin/matchdays/"})
    Iterable<MatchdayDto> findAll() {
        return StreamSupport.stream(this.matchdayService.findAll().spliterator(), false)
                .map(this::fromEntityToDto).collect(Collectors.toList());
    }

    @GetMapping(value = {"/matchdays/{id}", "/admin/matchdays/{id}"})
    MatchdayDto findById(@PathVariable Long id) {
        return fromEntityToDto(this.matchdayService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "MatchDay Not found")));
    }

    @GetMapping(value = {"/matchdays", "/admin/matchdays"})
    Iterable<MatchdayDto> findByCompetition(@RequestParam Optional<Long> competitionId) {
        if (competitionId.isPresent()) {
            return StreamSupport.stream(this.matchdayService.findByCompetition(competitionId.get()).spliterator(), false)
                    .map(this::fromEntityToDto).collect(Collectors.toList());
        }

        return findAll();
    }

    @PostMapping("/admin/matchdays/")
    MatchdayDto add(@RequestBody MatchdayDto matchdayDto) {
        return fromEntityToDto(this.matchdayService.add(fromDtoToEntity(matchdayDto)));
    }

    @PutMapping("/admin/matchdays/{id}")
    MatchdayDto update(@PathVariable Long id, @RequestBody MatchdayDto matchdayDto) {
        return fromEntityToDto(this.matchdayService.update(id, fromDtoToEntity(matchdayDto))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "MatchDay Not found")));
    }

    @DeleteMapping("/admin/matchdays/{id}")
    void delete(@PathVariable Long id) {
        if (!this.matchdayService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "MatchDay Not found");
        }
    }

    private MatchdayDto fromEntityToDto(Matchday matchday) {
        return modelMapper.map(matchday, MatchdayDto.class);
    }

    private Matchday fromDtoToEntity(MatchdayDto matchdayDto) {
        return modelMapper.map(matchdayDto, Matchday.class);
    }
}
