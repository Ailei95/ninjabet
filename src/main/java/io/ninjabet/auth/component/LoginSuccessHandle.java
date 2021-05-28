package io.ninjabet.auth.component;

import io.ninjabet.auth.entity.NinjaBetUser;
import io.ninjabet.auth.service.NinjaBetUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@AllArgsConstructor
@Component
public class LoginSuccessHandle extends SavedRequestAwareAuthenticationSuccessHandler {

    private final NinjaBetUserService ninjaBetUserService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {

        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails) {
            Optional<NinjaBetUser> ninjaBetUser = ninjaBetUserService.findById(((UserDetails) principal).getUsername());

            ninjaBetUserService.updateLastLoginDate(ninjaBetUser
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())));
        }

        response.setStatus(HttpStatus.OK.value());
    }
}
