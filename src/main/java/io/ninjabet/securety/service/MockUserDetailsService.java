package io.ninjabet.securety.service;

import io.ninjabet.auth.entity.User;
import io.ninjabet.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@Qualifier("MockDetailsService")
public class MockUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public MockUserDetailsService(UserService userService) { this.userService = userService; }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = this.userService.getUserByEmail(username);

        if (!user.isPresent()) {
            throw new UsernameNotFoundException("Username not found");
        }

        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                List<SimpleGrantedAuthority> authorities = new LinkedList<>();

                if (user.get().isAdmin()) {
                    authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                }

                return authorities;
            }

            @Override
            public String getPassword() { return "{noop}" + user.get().getPassword(); }

            @Override
            public String getUsername() {
                return user.get().getEmail();
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
                return true;
            }
        };
    }
}
