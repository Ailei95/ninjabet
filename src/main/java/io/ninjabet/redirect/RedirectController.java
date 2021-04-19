package io.ninjabet.redirect;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RedirectController {
    @GetMapping("/application/**")
    public ModelAndView redirectWithUsingRedirectPrefix(ModelMap model) {
        return new ModelAndView("redirect:/", model);
    }
}
