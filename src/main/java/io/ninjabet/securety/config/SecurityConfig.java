package io.ninjabet.securety.config;

import io.ninjabet.auth.component.AuthenticationFailureHandler;
import io.ninjabet.auth.component.LoginSuccessHandle;
import io.ninjabet.auth.component.LogoutSuccessHandle;
import io.ninjabet.securety.role.NinjaBetRole;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import java.util.Objects;

@AllArgsConstructor
@Configuration
// @EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final Environment environment;

    @Qualifier("NinjaBetDetailsService")
    private final UserDetailsService userDetailsService;

    private final LoginSuccessHandle loginSuccessHandle;

    private final LogoutSuccessHandle logoutSuccessHandle;

    private final AuthenticationFailureHandler authenticationFailureHandler;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        if (Objects.equals(environment.getProperty("spring.profiles.active"), "prod")) {
            httpSecurity.authorizeRequests()
                    .antMatchers("/api/admin").hasRole(NinjaBetRole.ADMIN.name())
                    .antMatchers("/api/admin/**").hasRole(NinjaBetRole.ADMIN.name())
                    .antMatchers("/").permitAll()
                    .and()
                        .formLogin().loginPage("/login")
                        .loginProcessingUrl("/api/login")
                        .successHandler(loginSuccessHandle)
                        .failureHandler(authenticationFailureHandler)
                        .and().rememberMe()
                    .and()
                        .logout().logoutUrl("/api/logout")
                        .logoutSuccessHandler(logoutSuccessHandle)
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                    .   deleteCookies("JSESSIONID", "remember-me")
                    .and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
                    //.and().sessionManagement().maximumSessions(1).maxSessionsPreventsLogin(true);
                    // disable cross request forgery token
                    // .and().csrf().disable();
        } else {
            httpSecurity.authorizeRequests()
                    .antMatchers("/").permitAll()
                    .and().formLogin().loginPage("/login.html")
                    .loginProcessingUrl("/login")
                    .and().csrf().disable();
        }
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService);
    }
}
