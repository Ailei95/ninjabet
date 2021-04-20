package io.ninjabet.football.controller;

import io.ninjabet.football.entity.Match;
import io.ninjabet.football.entity.Result;
import io.ninjabet.football.service.MatchService;
import io.ninjabet.football.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.LinkedList;
import java.util.List;
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

    @GetMapping(value = {"/results", "/admin/results"})
    Iterable<Result> findByMatchId(@RequestParam Optional<Long> matchId) {
        if (matchId.isPresent()) {
            List<Result> localResults = new LinkedList<>();
            Optional<Result> localResult = this.resultService.findByMatchId(matchId.get());

            localResult.ifPresent(localResults::add);

            return localResults;
        }

        return this.resultService.findAll();
    }

    @GetMapping(value = {"/results/{matchId}", "/admin/results/{matchId}"})
    Result findById(@PathVariable Long matchId) {
        Optional<Match> localMatch = this.matchService.findById(matchId);

        if (!localMatch.isPresent()) { throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Match not found"); }

        return this.resultService.findById(localMatch.get()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Result not found"));
    }

    @PostMapping("/admin/results/")
    Result add(@RequestBody Result result) {
        Optional<Match> localMatch = this.matchService.findById(result.getId());

        if (!localMatch.isPresent()) { throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Math not found"); }

        return this.resultService.add(result);
    }

    @PutMapping("/admin/results/{matchId}")
    Result update(@PathVariable Long matchId, @RequestBody Result result) {
        Optional<Match> localMatch = this.matchService.findById(matchId);

        return this.resultService.update(localMatch.get(), result).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Result not found"));
    }

    @DeleteMapping("/admin/results/{matchId}")
    void delete(@PathVariable Long matchId) {
        Optional<Match> localMatch = this.matchService.findById(matchId);

        if (!localMatch.isPresent() || !this.resultService.delete(localMatch.get())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Result or match not found");
        }
    }
}
