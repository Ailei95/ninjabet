package io.ninjabet.football.repository;

import io.ninjabet.football.entity.JMatch;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JMatchRepository extends CrudRepository<JMatch, Long> {
}
