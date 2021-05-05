package io.ninjabet.securety.config;

import io.ninjabet.auth.service.LoginSuccessHandle;
import io.ninjabet.securety.role.NinjaBetRole;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@AllArgsConstructor
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final Environment environment;

    @Qualifier("NinjaBetDetailsService")
    private final UserDetailsService userDetailsService;

    private final LoginSuccessHandle loginSuccessHandle;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        if (environment.getProperty("spring.profiles.active").equals("prod")) {
            httpSecurity.authorizeRequests()
                    .antMatchers("/api/admin").hasRole(NinjaBetRole.ADMIN.name())
                    .antMatchers("/api/admin/**").hasRole(NinjaBetRole.ADMIN.name())
                    .antMatchers("/").permitAll()
                    .and().formLogin().loginPage("/login.html")
                    .loginProcessingUrl("/api/login").successHandler(loginSuccessHandle)
                    .and().rememberMe()
                    .and().logout()
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID", "remember-me")
                    // .and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
                    // disable cross request forgery token
                    .and().csrf().disable();
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
