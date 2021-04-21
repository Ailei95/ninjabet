package io.ninjabet.football.repository;

import io.ninjabet.core.repository.DeleteManagerRepository;
import io.ninjabet.football.entity.Team;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends CrudRepository<Team, Long>, DeleteManagerRepository<Team> {
}
