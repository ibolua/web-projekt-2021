package de.hsrm.mi.web.projekt.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@SessionAttributes(names = { "loggedinusername" })
public class LoginController {
    private static final String LOGIN_STRING = "login"; // Compliant
    private static final String REDIRECT_SICHTUNG_MEINE_STRING = "redirect:/sichtung/meine"; // Compliant
    private static final String LOGGEDINUSERNAME_STRING = "loggedinusername"; // Compliant
    Logger logger = LoggerFactory.getLogger(LoginController.class);

    // Mit @ModelAttribute annotierte Methoden werden vor Requesthandler Methoden
    // aufgerufen
    // Model-Attribut 'loggedinusername' initialisieren (Session-Scope!)
    @ModelAttribute(LOGGEDINUSERNAME_STRING)
    public void initLoggedInUser(Model m) {
        m.addAttribute(LOGGEDINUSERNAME_STRING, "");
    }

    @GetMapping("")
    public String rootGet() {
        return LOGIN_STRING;
    }

    // GET auf http://localhost:8080/login
    @GetMapping("/login")
    public String loginGet(@ModelAttribute(LOGGEDINUSERNAME_STRING) String loggedinusername) {

        logger.info("GET LOGIN {}", loggedinusername);
        // if (!loggedinusername.isEmpty()) {
        //     return REDIRECT_SICHTUNG_MEINE_STRING;
        // }
        return LOGIN_STRING;
    }

    @PostMapping("/login")
    public String loginPost(Model m, @RequestParam String username, @RequestParam String password) {
        String pwMitLaenge = username + username.length();

        if (username.isEmpty()) {
            return LOGIN_STRING;
        }

        if (password.equals(pwMitLaenge)) {
            m.addAttribute("willkommenUser", "Willkommen " + username);
            m.addAttribute(LOGGEDINUSERNAME_STRING, username);
            return REDIRECT_SICHTUNG_MEINE_STRING;
        } else {
            m.addAttribute("username", username);
            m.addAttribute("pwMitLaenge", pwMitLaenge);
            m.addAttribute(LOGGEDINUSERNAME_STRING, "");
            logger.warn("Falsche Anmeldedaten für Username: {}", username);
            return LOGIN_STRING;
        }
    }

    @GetMapping("/logout")
    public String logout(SessionStatus sessionstatus) {
        // Session aktiv beenden
        sessionstatus.setComplete();
        return LOGIN_STRING;
    }

}
