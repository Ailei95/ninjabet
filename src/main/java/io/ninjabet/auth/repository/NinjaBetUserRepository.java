package io.ninjabet.auth.repository;

import io.ninjabet.auth.entity.NinjaBetUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NinjaBetUserRepository extends CrudRepository<NinjaBetUser, String> {
    Optional<NinjaBetUser> findByEmail(String email);
}
