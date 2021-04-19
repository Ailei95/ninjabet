package io.ninjabet.football.repository;

import io.ninjabet.football.entity.Competition;
import io.ninjabet.football.entity.Country;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompetitionRepository  extends CrudRepository<Competition, Long>, DeleteManagerRepository<Competition> {
    Iterable<Competition> findByCountry(Country country);
}
