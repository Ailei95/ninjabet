package io.ninjabet.football.repository;

import io.ninjabet.football.entity.Country;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends CrudRepository<Country, Long>, DeleteManagerRepository<Country> {
}
