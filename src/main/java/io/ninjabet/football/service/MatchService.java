package io.ninjabet.football.service;

import io.ninjabet.football.entity.Match;
import io.ninjabet.football.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class MatchService {

    private final MatchRepository matchRepository;

    @Autowired
    public MatchService(MatchRepository matchRepository) { this.matchRepository = matchRepository; }

    public Iterable<Match> getMatches() {
        return this.matchRepository.findAllByDeletedFalse();
    }

    public Optional<Match> getMatchById(Long id) {
        return this.matchRepository.findById(id);
    }

    public Match addMatch(Match match) {
        return this.matchRepository.save(match);
    }

    public Optional<Match> updateMatch(Long id, Match match) {
        Optional<Match> localMatch = this.matchRepository.findById(id);

        if (!localMatch.isPresent()) {
            return Optional.empty();
        }

        localMatch.get().setHome(match.getHome());
        localMatch.get().setGuest(match.getGuest());
        localMatch.get().setDate(match.getDate());
        localMatch.get().setMatchday(match.getMatchday());

        return Optional.of(this.matchRepository.save(localMatch.get()));
    }

    public boolean deleteMatch(Long id) {
        return this.setMatchIsDeleted(id, true);
    }

    public boolean restoreMatch(Long id) {
        return this.setMatchIsDeleted(id, false);
    }

    private boolean setMatchIsDeleted(Long id, boolean deleted) {
        Optional<Match> localMatch = this.matchRepository.findById(id);

        if (!localMatch.isPresent()) {
            return false;
        }

        if (deleted) {
            localMatch.get().setDeleteDate(new Date());
        } else {
            localMatch.get().setDeleteDate(null);
        }

        localMatch.get().setDeleted(deleted);

        this.matchRepository.save(localMatch.get());

        return true;
    }
}
