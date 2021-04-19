package io.ninjabet.football.repository;

import io.ninjabet.football.entity.Team;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends DeleteManagerCrudRepository<Team, Long> {
}
