package de.hsrm.mi.web.projekt.sichtung;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping("/sichtung")
// Attribut 'list' im Model soll im Session-Scope landen
@SessionAttributes(names = "meinesichtungen")
public class SichtungController {
    Logger logger = LoggerFactory.getLogger(SichtungController.class);



    // Model-Attribut 'liste' initialisieren (Session-Scope!)
    @ModelAttribute("meinesichtungen")
    public void initListe(Model m) {
        ArrayList<Sichtung> dieSicht = new ArrayList<>();
        List<String> headers = Arrays.asList("Datum", "Name", "Ort", "Beschreibung");
        LocalDate localDate = LocalDate.now();

        Sichtung a = new Sichtung();
        Sichtung b = new Sichtung();
        Sichtung c = new Sichtung();

        a.setName("A_Willi");
        a.setOrt("Vollradisroda");
        a.setDatum(localDate);
        a.setBeschreibung("Coole 17 am Ortseingang");


        b.setName("B_Herta");
        b.setOrt("Wuppertal");
        b.setDatum(localDate);
        b.setBeschreibung("Linie 17 gefahren");

        c.setName("C_Jokel");
        c.setOrt("Arensch");
        c.setDatum(localDate);
        c.setBeschreibung("17 Euro für eine Pizza gezahlt");

        dieSicht.add(a);
        dieSicht.add(b);
        dieSicht.add(c);

        m.addAttribute("meinesichtungen", dieSicht);
        m.addAttribute("headers", headers);
    }

    @GetMapping("/meine/liste")
    public String sichtungenMeineListeGet(){
        return "liste";
    }

    @GetMapping("/meine")
    public String sichtungMeineGet() {
        return "meinesichtung";
    }

}
