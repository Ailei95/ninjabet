package io.ninjabet.auth.repository;

import io.ninjabet.auth.entity.NinjaBetConfirmRegistrationToken;
import io.ninjabet.auth.entity.NinjaBetUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface NinjaBetConfirmRegistrationTokenRepository extends CrudRepository<NinjaBetConfirmRegistrationToken, Long> {

    Optional<NinjaBetConfirmRegistrationToken> findByToken(@Param("token") String token);

    Optional<NinjaBetConfirmRegistrationToken> findByUserAndConfirmedAtIsNullAndExpiresAtAfter
            (@Param("user") NinjaBetUser ninjaBetUser, @Param("after")LocalDateTime localDateTime);
}
