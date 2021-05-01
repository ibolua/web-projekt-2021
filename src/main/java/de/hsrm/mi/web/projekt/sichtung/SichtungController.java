package de.hsrm.mi.web.projekt.sichtung;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.support.SessionStatus;

@Controller
public class SichtungController {
    Logger logger = LoggerFactory.getLogger(SichtungController.class);

    @GetMapping("/sichtung/meine")
    public String sichtungMeineGet() {
        return "meinesichtung";
    }

}
