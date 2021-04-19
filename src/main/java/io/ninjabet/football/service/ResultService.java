package io.ninjabet.football.service;

import io.ninjabet.football.entity.Match;
import io.ninjabet.football.entity.Result;
import io.ninjabet.football.repository.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ResultService {

    private final ResultRepository resultRepository;

    private final MatchService matchService;

    @Autowired
    public ResultService(
            ResultRepository resultRepository,
            MatchService matchService
    ) {
        this.resultRepository = resultRepository;
        this.matchService = matchService;
    }

    public Iterable<Result> getResults() {
        return this.resultRepository.findAll();
    }

    public Optional<Result> getResultByMatch(Long matchId) {
        Optional<Match> localMatch = this.matchService.getMatchById(matchId);

//        if (!localMatch.isPresent()) {
//            return Optional.empty();
//        }
//
//        return this.resultRepository.findById(localMatch.get());

        return localMatch.flatMap(this.resultRepository::findById);
    }

    public Optional<Result> addResult(Result result) {
        Optional<Result> localResult = this.getResultByMatch(result.getId());

        if (localResult.isPresent()) {
            return Optional.empty();
        }

        return Optional.of(this.resultRepository.save(result));
    }

    public Optional<Result> updateResult(Long matchId, Result result) {
        Optional<Result> localResult = this.getResultByMatch(matchId);

        if (!localResult.isPresent()) {
            return Optional.empty();
        }

        return Optional.of(this.resultRepository.save(result));
    }

    public boolean deleteResult(Long matchId) {
        Optional<Result> localResult = this.getResultByMatch(matchId);

        if (!localResult.isPresent()) {
            return false;
        }

        this.resultRepository.delete(localResult.get());

        return true;
    }
}
