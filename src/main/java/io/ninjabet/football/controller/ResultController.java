package io.ninjabet.football.controller;

import io.ninjabet.football.entity.Match;
import io.ninjabet.football.entity.Result;
import io.ninjabet.football.service.MatchService;
import io.ninjabet.football.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ResultController {

    private final ResultService resultService;

    private final MatchService matchService;

    @Autowired
    public ResultController(
            ResultService resultService,
            MatchService matchService
    ) {
        this.resultService = resultService;
        this.matchService = matchService;
    }

    @GetMapping(value = {"/results/", "/admin/results/"})
    Iterable<Result> findAll() {
        return this.resultService.findAll();
    }

    @GetMapping(value = {"/results/{matchId}", "/admin/results/{matchId}"})
    Result findById(@PathVariable Long matchId) {
        return this.resultService.findById(matchId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Result not found"));
    }

    @PostMapping("/admin/results/")
    Result add(@RequestBody Result result) {
        return this.resultService.add(result);
    }

    @PutMapping("/admin/results/{matchId}")
    Result update(@PathVariable Long matchId, @RequestBody Result result) {
        return this.resultService.update(matchId, result).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Result not found"));
    }

    @DeleteMapping("/admin/results/{matchId}")
    void delete(@PathVariable Long matchId) {
        Optional<Match> localMatch = this.matchService.findById(matchId);

        if (!localMatch.isPresent() || !this.resultService.delete(matchId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Result or match not found");
        }
    }
}
