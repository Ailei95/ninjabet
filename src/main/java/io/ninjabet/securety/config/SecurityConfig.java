package io.ninjabet.securety.config;

import io.ninjabet.securety.role.NinjaBetRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final Environment environment;

    private final UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(
            Environment environment,
            // UserDetailService è un'interfaccia, senza l'annotazione qualifier spring non saprebbe quale servizio iniettare
            // perchè spring ha trovato 2 classi che hanno implementato tale interfaccia, qualifier appunto dice a spring di
            // iniettare nel costruttore la classe che ha l'etichetta MockDetailsService (MockDetailsService)
            @Qualifier("NinjaBetDetailsService") UserDetailsService userDetailsService
    ) {
        this.userDetailsService = userDetailsService;
        this.environment = environment;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        if (environment.getProperty("spring.profiles.active").equals("prod")) {
            httpSecurity.authorizeRequests()
                    .antMatchers("/api/admin").hasRole(NinjaBetRole.ADMIN.name())
                    .antMatchers("/api/admin/**").hasRole(NinjaBetRole.ADMIN.name())
                    // la valutazione di antMatchers viene troccato al primo path che combaccia
                    .antMatchers("/").permitAll()
                    // .and().httpBasic();
                    // redirect sulla pagina di login
                    .and().formLogin().loginPage("/login.html")
                    // .and().httpBasic()
                    // endpoint del servizio che verificherà le credenziali
                    .loginProcessingUrl("/api/login")
                    .and().rememberMe()
                    .and().logout()
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID", "remember-me")
                    // disable cross request forgery token
                    // .and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
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
