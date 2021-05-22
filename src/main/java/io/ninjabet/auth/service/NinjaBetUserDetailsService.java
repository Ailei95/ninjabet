package io.ninjabet.auth.service;

import io.ninjabet.auth.entity.NinjaBetConfirmRegistrationToken;
import io.ninjabet.auth.entity.NinjaBetUser;
import io.ninjabet.auth.entity.dto.NinjaBetUserDto;
import io.ninjabet.auth.repository.NinjaBetUserRepository;
import io.ninjabet.securety.permission.NinjaBetFootballPermission;
import io.ninjabet.securety.role.NinjaBetRole;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Qualifier("NinjaBetDetailsService")
public class NinjaBetUserDetailsService implements UserDetailsService  {

    private final NinjaBetUserRepository ninjaBetUserRepository;

    private final NinjaBetConfirmRegistrationTokenService ninjaBetConfirmRegistrationTokenService;

    private final PasswordEncoder passwordEncoder;

    private final MailSenderService mailSenderService;

    public Optional<NinjaBetUser> findById(String email) {
        return this.ninjaBetUserRepository.findById(email);
    }

    @Transactional
    public Optional<NinjaBetUser> add(NinjaBetUser ninjaBetUser) {
        LocalDateTime actionDate = LocalDateTime.now();

        Optional<NinjaBetUser> localUser = findById(ninjaBetUser.getEmail());

        if (localUser.isPresent()) {
            return Optional.empty();
        }

        ninjaBetUser.setAdmin(false);
        ninjaBetUser.setVerified(false);
        ninjaBetUser.setPassword(passwordEncoder.encode(ninjaBetUser.getPassword()));
        ninjaBetUser.setRegistrationDate(actionDate);
        ninjaBetUser.setLastPasswordChangeDate(actionDate);

        NinjaBetUser localNinjaBetUser =  this.ninjaBetUserRepository.save(ninjaBetUser);

        String token = UUID.randomUUID().toString();

        NinjaBetConfirmRegistrationToken ninjaBetConfirmRegistrationToken
                = new NinjaBetConfirmRegistrationToken(
                    token,
                    actionDate,
                    actionDate.plusMinutes(15L),
                    null,
                    localNinjaBetUser);

        ninjaBetConfirmRegistrationTokenService.add(ninjaBetConfirmRegistrationToken);

        try {
            mailSenderService.send(localNinjaBetUser.getEmail(),
                    "http://localhost:8080/api/registration?token=" + ninjaBetConfirmRegistrationToken.getToken());
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Cannot send email");
        }

        return Optional.of(localNinjaBetUser);
    }

    public void updateLastLoginDate(String email) {
        Optional<NinjaBetUser> localNinjaBetUser = findById(email);

        localNinjaBetUser.ifPresent(ninjaBetUser -> {
            ninjaBetUser.setLastLoginDate(LocalDateTime.now());
            ninjaBetUserRepository.save(localNinjaBetUser.get());
        });
    }

    public Optional<NinjaBetUser> confirmNinjaBetUser(String token) {
        Optional<NinjaBetConfirmRegistrationToken> ninjaBetConfirmRegistrationToken
                = this.ninjaBetConfirmRegistrationTokenService.findByToken(token);

        if (ninjaBetConfirmRegistrationToken.isPresent() && isTokenValid(ninjaBetConfirmRegistrationToken.get())) {

            Optional<NinjaBetUser> ninjaBetUser = this.findById(ninjaBetConfirmRegistrationToken.get().getUser().getEmail());

            ninjaBetUser.ifPresent(this::setVerified);

            return ninjaBetUser;
        }

        return Optional.empty();
    }

    private boolean isTokenValid(NinjaBetConfirmRegistrationToken ninjaBetConfirmRegistrationToken) {
        return ninjaBetConfirmRegistrationToken.getExpiresAt().compareTo(LocalDateTime.now()) > 0
                && ninjaBetConfirmRegistrationToken.getCreatedAt() == null;
    }

    private void setVerified(NinjaBetUser ninjaBetUser) {
        ninjaBetUser.setVerified(true);

        this.ninjaBetUserRepository.save(ninjaBetUser);
    }

    public Optional<NinjaBetUserDto> getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            UserDetails userDetails = loadUserByUsername(((UserDetails) principal).getUsername());

            NinjaBetUserDto ninjaBetUserDto = new NinjaBetUserDto();

            ninjaBetUserDto.setEmail(userDetails.getUsername());

            ninjaBetUserDto.setAuthorities(userDetails.getAuthorities().stream()
                    .map(Object::toString).collect(Collectors.toList())
            );

            return Optional.of(ninjaBetUserDto);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<NinjaBetUser> ninjaBetUser = this.findById(username);

        if (!ninjaBetUser.isPresent()) {
            throw new UsernameNotFoundException("Username not found");
        }

        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                List<SimpleGrantedAuthority> authorities = new LinkedList<>();

                if (ninjaBetUser.get().isAdmin()) {
                    authorities.add(new SimpleGrantedAuthority(String.format("ROLE_%s", NinjaBetRole.ADMIN)));

                    Set<NinjaBetFootballPermission> footballPermissions = NinjaBetRole.ADMIN.getFootballPermissions();

                    footballPermissions.forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission.name())));

                } else {
                    authorities.add(new SimpleGrantedAuthority(String.format("ROLE_%s", NinjaBetRole.USER)));

                    Set<NinjaBetFootballPermission> footballPermissions = NinjaBetRole.USER.getFootballPermissions();

                    footballPermissions.forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission.name())));

                }

                return authorities;
            }

            @Override
            public String getPassword() { return ninjaBetUser.get().getPassword(); }

            @Override
            public String getUsername() {
                return ninjaBetUser.get().getEmail();
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return ninjaBetUser.get().isVerified();
            }
        };
    }
}
