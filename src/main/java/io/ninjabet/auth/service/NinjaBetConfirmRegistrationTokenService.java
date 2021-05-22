package io.ninjabet.auth.service;

import io.ninjabet.auth.entity.NinjaBetConfirmRegistrationToken;
import io.ninjabet.auth.repository.NinjaBetConfirmRegistrationTokenRepository;
import io.ninjabet.core.service.CrudService;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NinjaBetConfirmRegistrationTokenService
        extends CrudService<NinjaBetConfirmRegistrationToken, Long, NinjaBetConfirmRegistrationTokenRepository> {

    private final NinjaBetConfirmRegistrationTokenRepository ninjaBetConfirmRegistrationTokenRepository;

    public NinjaBetConfirmRegistrationTokenService(
            NinjaBetConfirmRegistrationTokenRepository crudRepository,
            NinjaBetConfirmRegistrationTokenRepository ninjaBetConfirmRegistrationTokenRepository
    ) {
        super(crudRepository);
        this.ninjaBetConfirmRegistrationTokenRepository = ninjaBetConfirmRegistrationTokenRepository;
    }

    public Optional<NinjaBetConfirmRegistrationToken> findByToken(String token) {
        return this.ninjaBetConfirmRegistrationTokenRepository.findByToken(token);
    }
}
