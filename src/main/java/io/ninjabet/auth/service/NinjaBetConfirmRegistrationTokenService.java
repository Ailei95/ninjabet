package io.ninjabet.auth.service;

import io.ninjabet.auth.entity.NinjaBetConfirmRegistrationToken;
import io.ninjabet.auth.entity.NinjaBetUser;
import io.ninjabet.auth.repository.NinjaBetConfirmRegistrationTokenRepository;
import io.ninjabet.core.service.CrudService;

import io.ninjabet.core.service.MailSenderService;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class NinjaBetConfirmRegistrationTokenService
        extends CrudService<NinjaBetConfirmRegistrationToken, Long, NinjaBetConfirmRegistrationTokenRepository> {

    private static final long TOKEN_LIFETIME = 15L;

    private final NinjaBetConfirmRegistrationTokenRepository ninjaBetConfirmRegistrationTokenRepository;

    private final Environment environment;

    private final MailSenderService mailSenderService;

    public NinjaBetConfirmRegistrationTokenService(
            NinjaBetConfirmRegistrationTokenRepository crudRepository,
            NinjaBetConfirmRegistrationTokenRepository ninjaBetConfirmRegistrationTokenRepository,
            Environment environment,
            MailSenderService mailSenderService
    ) {
        super(crudRepository);
        this.ninjaBetConfirmRegistrationTokenRepository = ninjaBetConfirmRegistrationTokenRepository;
        this.environment = environment;
        this.mailSenderService = mailSenderService;
    }

    public Optional<NinjaBetConfirmRegistrationToken> findByToken(String token) {
        return this.ninjaBetConfirmRegistrationTokenRepository.findByToken(token);
    }

    public NinjaBetConfirmRegistrationToken generateRegistrationToken(NinjaBetUser ninjaBetUser) {
        Optional<NinjaBetConfirmRegistrationToken> ninjaBetConfirmRegistrationToken = getUnconfirmedValidRegistrationToken(ninjaBetUser);

        if (ninjaBetConfirmRegistrationToken.isPresent())
            return ninjaBetConfirmRegistrationToken.get();

        String token = UUID.randomUUID().toString();

        LocalDateTime actionDate = LocalDateTime.now();

        NinjaBetConfirmRegistrationToken registrationToken
                = new NinjaBetConfirmRegistrationToken(token, actionDate, actionDate.plusMinutes(TOKEN_LIFETIME), null, ninjaBetUser);

        return this.add(registrationToken);
    }

    public Optional<NinjaBetConfirmRegistrationToken> getUnconfirmedValidRegistrationToken(NinjaBetUser ninjaBetUser) {
        return this.ninjaBetConfirmRegistrationTokenRepository
                .findByUserAndConfirmedAtIsNullAndExpiresAtAfter(ninjaBetUser, LocalDateTime.now());
    }

    public void sendConfirmationEmail(String email, String token) {
        String hostname = environment.getProperty("ninjabet.hostname");

        String port = environment.getProperty("server.port");

        String body = String.format("http://%s:%s/api/registration?token=%s", hostname, port, token);

        mailSenderService.send(email, body);
    }
}
