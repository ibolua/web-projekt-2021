package de.hsrm.mi.web.projekt.auswahl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuswahlController {

    @GetMapping("/auswahl")
    public String getStartLoginPage() {
        return "auswahl/auswahl";
    }

}
