package io.ninjabet.football.repository;

import io.ninjabet.football.entity.Competition;
import io.ninjabet.football.entity.Country;
import org.springframework.stereotype.Repository;

@Repository
public interface CompetitionRepository extends DeleteManagerCrudRepository<Competition, Long> {
    Iterable<Competition> findByCountry(Country country);
}
