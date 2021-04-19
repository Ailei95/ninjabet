package io.ninjabet.football.repository;

import io.ninjabet.football.entity.Country;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends DeleteManagerCrudRepository<Country, Long> {
}
