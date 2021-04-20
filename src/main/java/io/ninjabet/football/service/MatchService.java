package io.ninjabet.football.service;

import io.ninjabet.auth.service.UserService;
import io.ninjabet.football.entity.Match;
import io.ninjabet.football.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchService extends CrudService<Match, Long, MatchRepository> {

    @Autowired
    public MatchService(
            MatchRepository matchRepository,
            UserService userService
    ) {
        super(matchRepository, userService);
    }
}
