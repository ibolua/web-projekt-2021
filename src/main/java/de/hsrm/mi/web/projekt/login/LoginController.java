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
    private static final String LOGING_STRING = "login"; // Compliant
    private static final String REDIRECT_SICHTUNG_MEINE_STRING = "redirect:/sichtung/meine"; // Compliant
    Logger logger = LoggerFactory.getLogger(LoginController.class);

    // Mit @ModelAttribute annotierte Methoden werden vor Requesthandler Methoden
    // aufgerufen
    // Model-Attribut 'liste' initialisieren (Session-Scope!)
    @ModelAttribute("loggedinusername")
    public void initLoggedInUser(Model m) {
        m.addAttribute("loggedinusername", "");
    }

    // GET auf http://localhost:8080/login
    @GetMapping("/login")
    public String loginGet(@ModelAttribute("loggedinusername") String loggedinusername) {

        logger.error("GET LOGIN {}", loggedinusername);
        if (!loggedinusername.isEmpty()) {
            return REDIRECT_SICHTUNG_MEINE_STRING;
        }
        return LOGING_STRING;
    }

    @PostMapping("/login")
    public String loginPost(Model m, @RequestParam String username, @RequestParam String password) {

        String pwMitLaenge = username + username.length();
        String hinweis = "Hinweis: Das korrekte Passwort für " + username + " ist " + pwMitLaenge;

        if (username.isEmpty()) {
            return LOGING_STRING;
        }

        if (password.equals(pwMitLaenge)) {
            m.addAttribute("willkommenUser", "Willkommen " + username);
            m.addAttribute("loggedinusername", username);
            return REDIRECT_SICHTUNG_MEINE_STRING;
        } else {
            m.addAttribute("hinweis", hinweis);
            m.addAttribute("loggedinusername", "");
            logger.warn("Falsche Anmeldedaten für Username: {}", username);
            return LOGING_STRING;
        }
    }



    @GetMapping("/logout")
    public String logout(SessionStatus sessionstatus) {
        // Session aktiv beenden
        sessionstatus.setComplete();
        return LOGING_STRING;
    }

}