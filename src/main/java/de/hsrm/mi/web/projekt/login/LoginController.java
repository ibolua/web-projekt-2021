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
    Logger logger = LoggerFactory.getLogger(LoginController.class);

    // Mit @ModelAttribute annotierte Methoden werden vor Requesthandler Methoden
    // aufgerufen
    // Model-Attribut 'liste' initialisieren (Session-Scope!)
    @ModelAttribute("loggedinusername")
    public void initListe(Model m) {
        m.addAttribute("loggedinusername", new LoggedInUsername());
    }

    // GET auf http://localhost:8080/login
    @GetMapping("/login")
    public String loginGet() {
        return LOGING_STRING;
    }

    @PostMapping("/login")
    public String loginPost(Model m, @RequestParam String username, @RequestParam String password,
            @ModelAttribute("LoggedInUsername") LoggedInUsername lst) {

        int len = username.length();
        String pwMitLaenge = username + Integer.toString(len);
        String hinweis = "Hinweis: Das korrekte Passwort für " + username + " ist " + pwMitLaenge;

        if (username.isEmpty()) {
            return LOGING_STRING;
        }

        if (password.equals(pwMitLaenge)) {
            lst.add(username);
            return "redirect:/sichtung/meine";
            // return "pwrichtig"; // pwRichtig View
        } else {
            logger.warn("Falsche Anmeldedaten für Username: {}", username);
            m.addAttribute("hinweis", hinweis);

            lst.add("");
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