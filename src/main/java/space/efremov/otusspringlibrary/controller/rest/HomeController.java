package space.efremov.otusspringlibrary.controller.rest;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    private static final String LOGIN_PAGE = "login/index";
    private static final String HOME_PAGE = "index";

    @GetMapping
    public String home() {
        if (SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().isAuthenticated())
            return HOME_PAGE;

        return LOGIN_PAGE;
    }
}
