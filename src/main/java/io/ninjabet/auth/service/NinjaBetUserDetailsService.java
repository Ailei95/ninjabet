package io.ninjabet.auth.service;

import io.ninjabet.auth.entity.NinjaBetUser;
import io.ninjabet.auth.repository.NinjaBetUserRepository;
import io.ninjabet.securety.permission.NinjaBetFootballPermission;
import io.ninjabet.securety.role.NinjaBetRole;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@AllArgsConstructor
@Service
@Qualifier("NinjaBetDetailsService")
public class NinjaBetUserDetailsService implements UserDetailsService  {

    private final NinjaBetUserRepository ninjaBetUserRepository;

    private final PasswordEncoder passwordEncoder;

    public Optional<NinjaBetUser> findById(String email) {
        return this.ninjaBetUserRepository.findById(email);
    }

    public Optional<NinjaBetUser> add(NinjaBetUser ninjaBetUser) {
        Date actionDate = new Date();
        Optional<NinjaBetUser> localUser = findById(ninjaBetUser.getEmail());

        if (localUser.isPresent()) {
            return Optional.empty();
        }

        ninjaBetUser.setAdmin(false);
        ninjaBetUser.setVerify(false);
        ninjaBetUser.setPassword(passwordEncoder.encode(ninjaBetUser.getPassword()));
        ninjaBetUser.setRegistrationDate(actionDate);
        ninjaBetUser.setLastPasswordChangeDate(actionDate);

        return Optional.of(this.ninjaBetUserRepository.save(ninjaBetUser));
    }

    public void updateLastLoginDate(String email) {
        Optional<NinjaBetUser> localNinjaBetUser = findById(email);

        localNinjaBetUser.ifPresent(ninjaBetUser -> {
            ninjaBetUser.setLastLoginDate(new Date());
            ninjaBetUserRepository.save(localNinjaBetUser.get());
        });
    }

    public Optional<NinjaBetUser> getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return (principal instanceof UserDetails) ? findById(((UserDetails) principal).getUsername()) : Optional.empty();
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
                return ninjaBetUser.get().isVerify();
            }
        };
    }
}
