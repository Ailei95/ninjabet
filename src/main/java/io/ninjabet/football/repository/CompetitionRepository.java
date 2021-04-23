package io.ninjabet.football.repository;

import io.ninjabet.football.entity.Competition;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CompetitionRepository extends CrudRepository<Competition, Long> {
    Iterable<Competition> findByCountry_Id(@Param("countryId") Long countryId);
}
