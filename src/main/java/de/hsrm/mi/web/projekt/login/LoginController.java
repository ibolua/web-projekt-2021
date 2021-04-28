package de.hsrm.mi.web.projekt.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    private static final String LOGING_STRING = "login"; // Compliant
    Logger logger = LoggerFactory.getLogger(LoginController.class);

    // GET auf http://localhost:8080/login
    @GetMapping("/login")
    public String loginGet() {
        return LOGING_STRING;
    }

    @PostMapping("/login")
    public String loginPost(Model m, @RequestParam("username") String username,
            @RequestParam("password") String password) {
        
        int len = username.length();
        String pwMitLaenge = username + Integer.toString(len);
        String hinweis = "Hinweis: Das korrekte Passwort für " + username + " ist " + pwMitLaenge;

        if (username.isEmpty()) {
            return LOGING_STRING;
        }

        if (password.equals(pwMitLaenge)) {
            return "pwrichtig"; // pwRichtig View
        }
        logger.warn("Falsche Anmeldedaten für Username: {}", username);

        m.addAttribute("hinweis", hinweis);

        return LOGING_STRING;
    }

}