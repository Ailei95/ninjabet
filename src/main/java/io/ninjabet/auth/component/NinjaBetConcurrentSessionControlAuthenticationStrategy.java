package io.ninjabet.auth.component;

import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.stereotype.Controller;

@Controller
public class NinjaBetConcurrentSessionControlAuthenticationStrategy extends ConcurrentSessionControlAuthenticationStrategy {

    public NinjaBetConcurrentSessionControlAuthenticationStrategy() {
        super(new SessionRegistryImpl());
    }
}
