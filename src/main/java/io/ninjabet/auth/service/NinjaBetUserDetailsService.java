package io.ninjabet.auth.service;

import io.ninjabet.auth.entity.NinjaBetUser;
import io.ninjabet.auth.entity.dto.NinjaBetUserDto;
import io.ninjabet.securety.permission.NinjaBetFootballPermission;
import io.ninjabet.securety.role.NinjaBetRole;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Qualifier("NinjaBetDetailsService")
public class NinjaBetUserDetailsService implements UserDetailsService  {

    private final NinjaBetUserService ninjaBetUserService;

    public Optional<NinjaBetUser> getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return principal instanceof UserDetails ?
                this.ninjaBetUserService.findById(((UserDetails) principal).getUsername()) : Optional.empty();
    }

    public Optional<NinjaBetUserDto> getCurrentUserDto() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Object principal = auth.getPrincipal();

        if (principal instanceof UserDetails) {
            UserDetails userDetails = loadUserByUsername(((UserDetails) principal).getUsername());

            NinjaBetUserDto ninjaBetUserDto = new NinjaBetUserDto();

            ninjaBetUserDto.setEmail(userDetails.getUsername());

            ninjaBetUserDto.setAuthorities(userDetails.getAuthorities().stream()
                    .filter(authority -> authority.toString().startsWith("ROLE_"))
                    .map(Object::toString).collect(Collectors.toList())
            );

            Authentication newAuth
                    = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(newAuth);

            return Optional.of(ninjaBetUserDto);
        } else {
            return Optional.empty();
        }
    }

    public void updateUserDetails(String email) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        UserDetails userDetails = this.loadUserByUsername(email);

        Authentication newAuth
                = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<NinjaBetUser> ninjaBetUser = this.ninjaBetUserService.findById(username);

        if (!ninjaBetUser.isPresent())
            throw new UsernameNotFoundException("Username not found");

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
