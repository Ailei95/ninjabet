package io.ninjabet.football.service;

import io.ninjabet.football.entity.JMatch;
import io.ninjabet.football.repository.JMatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JMatchService {

    private final JMatchRepository jMatchRepository;

    @Autowired
    public JMatchService(JMatchRepository jMatchRepository) { this.jMatchRepository = jMatchRepository; }

    public Iterable<JMatch> getJMatches() {
        return this.jMatchRepository.findAll();
    }

    public Optional<JMatch> getJMatchById(Long id) {
        return this.jMatchRepository.findById(id);
    }

    public JMatch addJMatch(JMatch jMatch) {
        return this.jMatchRepository.save(jMatch);
    }

    public Optional<JMatch> updateJMatch(Long id, JMatch jMatch) {
        Optional<JMatch> localJMatch = this.jMatchRepository.findById(id);

        if (!localJMatch.isPresent()) {
            Optional.empty();
        }

        localJMatch.get().setHome(jMatch.getHome());
        localJMatch.get().setGuest(jMatch.getGuest());
        localJMatch.get().setDate(jMatch.getDate());
        localJMatch.get().setMatchDay(jMatch.getMatchDay());

        return Optional.of(this.jMatchRepository.save(localJMatch.get()));
    }

    public boolean deleteJMatch(Long id) {
        Optional<JMatch> jMatch = this.jMatchRepository.findById(id);

        if (!jMatch.isPresent()) {
            return false;
        }

        this.jMatchRepository.delete(jMatch.get());

        return true;
    }
}
