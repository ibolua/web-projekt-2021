package de.hsrm.mi.web.projekt.fotologin;

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
public class FotoLoginController {
    private static final String LOGING_STRING = "foto/login"; // Compliant
    private static final String REDIRECT_FOTO_STRING = "redirect:/foto"; // Compliant
    private static final String LOGGEDINUSERNAME_STRING = "loggedinusername"; // Compliant
    Logger logger = LoggerFactory.getLogger(FotoLoginController.class);

    // Mit @ModelAttribute annotierte Methoden werden vor Requesthandler Methoden
    // aufgerufen
    // Model-Attribut 'loggedinusername' initialisieren (Session-Scope!)
    @ModelAttribute(LOGGEDINUSERNAME_STRING)
    public void initLoggedInUser(Model m) {
        m.addAttribute(LOGGEDINUSERNAME_STRING, "");
    }

    // GET auf http://localhost:8080/login
    @GetMapping("/fotologin")
    public String loginGet(@ModelAttribute(LOGGEDINUSERNAME_STRING) String loggedinusername) {

        logger.info("GET LOGIN {}", loggedinusername);
        if (!loggedinusername.isEmpty()) {
            return REDIRECT_FOTO_STRING;
        }
        return LOGING_STRING;
    }

    @PostMapping("/fotologin")
    public String loginPost(Model m, @RequestParam String username, @RequestParam String password) {
        String pwMitLaenge = username + username.length();

        if (username.isEmpty()) {
            return LOGING_STRING;
        }

        if (password.equals(pwMitLaenge)) {
            logger.warn("RICHTIGE Anmeldedaten für Username: {}", username);
            m.addAttribute("willkommenUser", "Willkommen " + username);
            m.addAttribute(LOGGEDINUSERNAME_STRING, username);
            return REDIRECT_FOTO_STRING;
        } else {
            m.addAttribute("username", username);
            m.addAttribute("pwMitLaenge", pwMitLaenge);
            m.addAttribute(LOGGEDINUSERNAME_STRING, "");
            logger.warn("FALSCHE Anmeldedaten für Username: {}", username);
            return LOGING_STRING;
        }
    }

    @GetMapping("/logoutfoto")
    public String logoutfoto(SessionStatus sessionstatus) {
        // Session aktiv beenden
        sessionstatus.setComplete();
        return LOGING_STRING;
    }

}
