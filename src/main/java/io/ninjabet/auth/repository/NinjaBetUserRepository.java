package io.ninjabet.auth.repository;

import io.ninjabet.auth.entity.NinjaBetUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NinjaBetUserRepository extends CrudRepository<NinjaBetUser, String> {
}
