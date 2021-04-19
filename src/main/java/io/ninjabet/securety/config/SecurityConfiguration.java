package io.ninjabet.securety.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final Environment environment;

    private final UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfiguration(
            Environment environment,
            // UserDetailService è un'interfaccia, senza l'annotazione qualifier spring non saprebbe quale servizio iniettare
            // perchè spring ha trovato 2 classi che hanno implementato tale interfaccia, qualifier appunto dice a spring di
            // iniettare nel costruttore la classe che ha l'etichetta MockDetailsService (MockDetailsService)
            @Qualifier("MockDetailsService") UserDetailsService userDetailsService
    ) {
        this.userDetailsService = userDetailsService;
        this.environment = environment;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        if (environment.getProperty("spring.profiles.active").equals("prod")) {
            httpSecurity.authorizeRequests()
                    .antMatchers("/api/admin").hasRole("ADMIN")
                    .antMatchers("/api/admin/**").hasRole("ADMIN")
                    // la valutazione di antMatchers viene troccato al primo path che combaccia
                    .antMatchers("/").permitAll()
                    // redirect sulla pagina di login
                    .and().formLogin().loginPage("/login.html")
                    // endpoint del servizio che verificherà le credenziali
                    .loginProcessingUrl("/login")
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
