package io.ninjabet.football.service;

import io.ninjabet.auth.service.UserService;
import io.ninjabet.football.entity.Match;
import io.ninjabet.football.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MatchService extends DeleteManagerService<Match, Long> {

    @Autowired
    public MatchService(
            MatchRepository matchRepository,
            UserService userService
    ) {
        super(matchRepository, userService);
    }

    public Iterable<Match> getMatches() {
        return ((MatchRepository) this.crudRepository).findAllByDeletedFalse();
    }

    public Optional<Match> getMatchById(Long id) {
        return this.crudRepository.findById(id);
    }

    public Match addMatch(Match match) {
        return this.crudRepository.save(match);
    }

    public Optional<Match> updateMatch(Long id, Match match) {
        Optional<Match> localMatch = this.crudRepository.findById(id);

        if (!localMatch.isPresent()) {
            return Optional.empty();
        }

        localMatch.get().setHome(match.getHome());
        localMatch.get().setGuest(match.getGuest());
        localMatch.get().setDate(match.getDate());
        localMatch.get().setMatchday(match.getMatchday());

        return Optional.of(this.crudRepository.save(localMatch.get()));
    }

    public boolean deleteMatch(Long id) {
        return this.setEntityDeleted(id, true);
    }

    public boolean restoreMatch(Long id) {
        return this.setEntityDeleted(id, false);
    }
}
