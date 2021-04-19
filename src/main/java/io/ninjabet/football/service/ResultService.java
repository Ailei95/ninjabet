package io.ninjabet.football.service;

import io.ninjabet.auth.service.UserService;
import io.ninjabet.football.entity.Match;
import io.ninjabet.football.entity.Result;
import io.ninjabet.football.repository.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ResultService extends DeleteManagerService<Result, Match, ResultRepository> {

    private final MatchService matchService;

    @Autowired
    public ResultService(
            ResultRepository resultRepository,
            MatchService matchService,
            UserService userService
    ) {
        super(resultRepository, userService);
        this.matchService = matchService;
    }

    public Iterable<Result> getResults() {
        return this.crudRepository.findAllByDeletedFalse();
    }

    public Optional<Result> getResultByMatch(Long matchId) {
        Optional<Match> localMatch = this.matchService.getMatchById(matchId);

//        if (!localMatch.isPresent()) {
//            return Optional.empty();
//        }
//
//        return this.resultRepository.findById(localMatch.get());

        return localMatch.flatMap(this.crudRepository::findById);
    }

    public Optional<Result> addResult(Result result) {
        Optional<Result> localResult = this.getResultByMatch(result.getId());

        if (localResult.isPresent()) {
            return Optional.empty();
        }

        return Optional.of(this.crudRepository.save(result));
    }

    public Optional<Result> updateResult(Long matchId, Result result) {
        Optional<Result> localResult = this.getResultByMatch(matchId);

        if (!localResult.isPresent()) {
            return Optional.empty();
        }

        return Optional.of(this.crudRepository.save(result));
    }

    public boolean deleteResult(Long matchId) {
        Optional<Match> localMatch = this.matchService.getMatchById(matchId);
        return localMatch.filter(match -> this.setEntityDeleted(match, true)).isPresent();
    }

    public boolean restoreResult(Long matchId) {
        Optional<Match> localMatch = this.matchService.getMatchById(matchId);
        return localMatch.filter(match -> this.setEntityDeleted(match, false)).isPresent();
    }
}
