package io.ninjabet.football.controller;

import io.ninjabet.football.entity.Match;
import io.ninjabet.football.entity.Result;
import io.ninjabet.football.entity.Team;
import io.ninjabet.football.entity.dto.ResultDto;
import io.ninjabet.football.entity.dto.TeamDto;
import io.ninjabet.football.service.MatchService;
import io.ninjabet.football.service.ResultService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class ResultController {

    private final ResultService resultService;

    private final MatchService matchService;

    private final ModelMapper modelMapper;

    @GetMapping(value = {"/results/", "/admin/results/"})
    Iterable<ResultDto> findAll() {
        return StreamSupport.stream(this.resultService.findAll().spliterator(), false)
                .map(this::fromEntityToDto).collect(Collectors.toList());
    }

    @GetMapping(value = {"/results/{matchId}", "/admin/results/{matchId}"})
    ResultDto findById(@PathVariable Long matchId) {
        return fromEntityToDto(this.resultService.findById(matchId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Result not found")));
    }

    @PostMapping("/admin/results/")
    ResultDto add(@RequestBody ResultDto resultDto) {
        return fromEntityToDto(this.resultService.add(fromDtoToEntity(resultDto)));
    }

    @PutMapping("/admin/results/{matchId}")
    ResultDto update(@PathVariable Long matchId, @RequestBody ResultDto resultDto) {
        return fromEntityToDto(this.resultService.update(matchId, fromDtoToEntity(resultDto))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Result not found")));
    }

    @DeleteMapping("/admin/results/{matchId}")
    void delete(@PathVariable Long matchId) {
        Optional<Match> localMatch = this.matchService.findById(matchId);

        if (!localMatch.isPresent() || !this.resultService.delete(matchId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Result or match not found");
        }
    }

    private ResultDto fromEntityToDto(Result result) {
        return modelMapper.map(result, ResultDto.class);
    }

    private Result fromDtoToEntity(ResultDto resultDto) {
        return modelMapper.map(resultDto, Result.class);
    }
}
