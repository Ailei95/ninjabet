package io.ninjabet.auth.component;

import io.ninjabet.auth.entity.NinjaBetConfirmRegistrationToken;
import io.ninjabet.auth.entity.NinjaBetUser;
import io.ninjabet.auth.service.NinjaBetConfirmRegistrationTokenService;
import io.ninjabet.auth.service.NinjaBetUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@AllArgsConstructor
@Component
public class AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final NinjaBetUserService ninjaBetUserService;

    private final NinjaBetConfirmRegistrationTokenService ninjaBetConfirmRegistrationTokenService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {

        if (exception instanceof BadCredentialsException) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Username or password is wrong");
        } else if (exception instanceof DisabledException) {

            Optional<NinjaBetUser> ninjaBetUser = ninjaBetUserService.findById(request.getParameter("username"));

            Optional<NinjaBetConfirmRegistrationToken> ninjaBetConfirmRegistrationToken
                    = ninjaBetConfirmRegistrationTokenService.getUnconfirmedValidRegistrationToken(ninjaBetUser.orElse(null));

            if (ninjaBetConfirmRegistrationToken.isPresent())
                response.sendError(HttpStatus.LOCKED.value(), "Account not verified");
            else
                response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());

        } else {
            super.onAuthenticationFailure(request, response, exception);
        }
    }
}
