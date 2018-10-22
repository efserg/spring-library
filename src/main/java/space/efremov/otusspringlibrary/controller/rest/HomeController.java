package space.efremov.otusspringlibrary.controller.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    private static final String LOGIN_PAGE = "login/index";

    @GetMapping
    public String loginPage() {
        return LOGIN_PAGE;
    }
}
