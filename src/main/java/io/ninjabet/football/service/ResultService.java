package io.ninjabet.football.service;

import io.ninjabet.auth.service.UserService;
import io.ninjabet.football.entity.Match;
import io.ninjabet.football.entity.Result;
import io.ninjabet.football.repository.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ResultService extends CrudService<Result, Match, ResultRepository> {

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

    public Optional<Result> findByMatchId(Long matchId) {
        Optional<Match> localMatch = this.matchService.findById(matchId);

        return localMatch.flatMap(this.crudRepository::findById);
    }
}
