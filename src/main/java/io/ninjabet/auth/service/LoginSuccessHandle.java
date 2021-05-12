package io.ninjabet.auth.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
@Component
public class LoginSuccessHandle extends SavedRequestAwareAuthenticationSuccessHandler {

    private final NinjaBetUserDetailsService ninjaBetUserDetailsService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails)
            ninjaBetUserDetailsService.updateLastLoginDate(((UserDetails) principal).getUsername());

        response.setStatus(200);

        response.getWriter().flush();

        // super.onAuthenticationSuccess(request, response, authentication);
    }
}
