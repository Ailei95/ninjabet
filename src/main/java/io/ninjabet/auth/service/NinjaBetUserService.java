package io.ninjabet.auth.service;

import io.ninjabet.auth.entity.NinjaBetConfirmRegistrationToken;
import io.ninjabet.auth.entity.NinjaBetUser;
import io.ninjabet.auth.repository.NinjaBetUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@AllArgsConstructor
@Service
public class NinjaBetUserService {

    private final NinjaBetUserRepository ninjaBetUserRepository;

    private final NinjaBetConfirmRegistrationTokenService ninjaBetConfirmRegistrationTokenService;

    private final PasswordEncoder passwordEncoder;

    public Optional<NinjaBetUser> findById(String email) {
        return this.ninjaBetUserRepository.findById(email);
    }

    @Transactional
    public Optional<NinjaBetConfirmRegistrationToken> add(NinjaBetUser ninjaBetUser) {
        Optional<NinjaBetUser> localUser = findById(ninjaBetUser.getEmail());

        if (localUser.isPresent()) {
            boolean isPresentUnconfirmedValidRegistrationToken
                    = ninjaBetConfirmRegistrationTokenService.getUnconfirmedValidRegistrationToken(localUser.get()).isPresent();

            if (localUser.get().isVerified() || isPresentUnconfirmedValidRegistrationToken)
                return Optional.empty();
        }

        LocalDateTime actionDate = LocalDateTime.now();

        ninjaBetUser.setAdmin(false);
        ninjaBetUser.setVerified(false);
        ninjaBetUser.setPassword(passwordEncoder.encode(ninjaBetUser.getPassword()));
        ninjaBetUser.setRegistrationDate(actionDate);
        ninjaBetUser.setLastPasswordChangeDate(null);

        NinjaBetUser localNinjaBetUser =  this.ninjaBetUserRepository.save(ninjaBetUser);

        NinjaBetConfirmRegistrationToken registrationToken
                = ninjaBetConfirmRegistrationTokenService.generateRegistrationToken(localNinjaBetUser);

        this.ninjaBetConfirmRegistrationTokenService.sendConfirmationEmail(localNinjaBetUser.getEmail(), registrationToken.getToken());

        return Optional.of(registrationToken);
    }

    public NinjaBetUser updateLastLoginDate(NinjaBetUser ninjaBetUser) {
        ninjaBetUser.setLastLoginDate(LocalDateTime.now());
        return ninjaBetUserRepository.save(ninjaBetUser);
    }

    // TODO Delete

    public NinjaBetUser setAdmin(NinjaBetUser ninjaBetUser, boolean isAdmin) {
        ninjaBetUser.setAdmin(isAdmin);
        return ninjaBetUserRepository.save(ninjaBetUser);
    }

    @Transactional
    public Optional<NinjaBetUser> confirmNinjaBetUser(String token) {
        Optional<NinjaBetConfirmRegistrationToken> ninjaBetConfirmRegistrationToken
                = this.ninjaBetConfirmRegistrationTokenService.findByToken(token);

        if (ninjaBetConfirmRegistrationToken.isPresent() && isTokenValid(ninjaBetConfirmRegistrationToken.get())) {

            Optional<NinjaBetUser> ninjaBetUser = this.findById(ninjaBetConfirmRegistrationToken.get().getUser().getEmail());

            ninjaBetUser.ifPresent((user) -> {
                this.setVerified(user);
                this.setConfirmed(ninjaBetConfirmRegistrationToken.get());
            });

            return ninjaBetUser;
        }

        return Optional.empty();
    }

    private boolean isTokenValid(NinjaBetConfirmRegistrationToken ninjaBetConfirmRegistrationToken) {
        return ninjaBetConfirmRegistrationToken.getExpiresAt().compareTo(LocalDateTime.now()) > 0
                && ninjaBetConfirmRegistrationToken.getConfirmedAt() == null;
    }

    @SuppressWarnings("UnusedReturnValue")
    private NinjaBetUser setVerified(NinjaBetUser ninjaBetUser) {
        ninjaBetUser.setVerified(true);

        return this.ninjaBetUserRepository.save(ninjaBetUser);
    }

    @SuppressWarnings("UnusedReturnValue")
    private NinjaBetConfirmRegistrationToken setConfirmed(NinjaBetConfirmRegistrationToken ninjaBetConfirmRegistrationToken) {
        ninjaBetConfirmRegistrationToken.setConfirmedAt(LocalDateTime.now());

        return this.ninjaBetConfirmRegistrationTokenService
                .update(ninjaBetConfirmRegistrationToken.getId(), ninjaBetConfirmRegistrationToken).orElse(null);
    }
}
