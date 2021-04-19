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

    // private final MatchService matchService;

    @Autowired
    public ResultController(
            ResultService resultService
            // MatchService matchService
    ) {
        this.resultService = resultService;
        // this.matchService = matchService;
    }

    @GetMapping(value = {"/results/", "/admin/results/"})
    Iterable<Result> getResults() {
        return this.resultService.getResults();
    }

    @GetMapping(value = {"/results", "/admin/results"})
    Iterable<Result> getResultsByMatchId(@RequestParam Optional<Long> matchId) {
        if (matchId.isPresent()) {
            List<Result> localResults = new LinkedList<>();
            Optional<Result> localResult = this.resultService.getResultByMatch(matchId.get());

            localResult.ifPresent(localResults::add);

            return localResults;
        }

        return this.resultService.getResults();
    }

    @GetMapping(value = {"/results/{matchId}", "/admin/results/{matchId}"})
    Result getResultById(@PathVariable  Long matchId) {
        return this.resultService.getResultByMatch(matchId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Result not found"));
    }

    @PostMapping("/admin/results/")
    Result addResult(@RequestBody Result result) {
        return this.resultService.addResult(result).orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT, "Result already exists"));
    }

    @PutMapping("/admin/results/{matchId}")
    Result updateResult(@PathVariable Long matchId, @RequestBody Result result) {
        return this.resultService.updateResult(matchId, result).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Result not found"));
    }

    @DeleteMapping("/admin/results/{matchId}")
    void deleteResult(@PathVariable Long matchId) {
//        Optional<Match> localMatch = this.matchService.getMatchById(matchId);
//
//        if (localMatch.isPresent() && !this.resultService.deleteResult(localMatch.get())) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Result not found");
//        }
    }
}
