package io.ninjabet.auth.repository;

import io.ninjabet.auth.entity.NinjaBetConfirmRegistrationToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface NinjaBetConfirmRegistrationTokenRepository extends CrudRepository<NinjaBetConfirmRegistrationToken, Long> {

    Optional<NinjaBetConfirmRegistrationToken> findByToken(@Param("token") String token);
}
