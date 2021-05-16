package de.hsrm.mi.web.projekt.sichtung;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping("/sichtung")
// Attribut 'meinesichtungen' im Model soll im Session-Scope landen
@SessionAttributes(names = { "meinesichtungen" })
public class SichtungController {
    Logger logger = LoggerFactory.getLogger(SichtungController.class);
    private static final List<String> HEADERS = Arrays.asList("Datum", "Name", "Ort", "Beschreibung");

    // Model-Attribut 'meinesichtungen' initialisieren (Session-Scope!)
    @ModelAttribute("meinesichtungen")
    public void initListe(Model m, @ModelAttribute("loggedinusername") String loggedinusername) {
        ArrayList<Sichtung> dieSicht = new ArrayList<>();

        LocalDate localDate = LocalDate.now();

        Sichtung a = new Sichtung();
        Sichtung b = new Sichtung();
        Sichtung c = new Sichtung();
        Sichtung d = new Sichtung();
        Sichtung e = new Sichtung();

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

        d.setName("D_Peter");
        d.setOrt("Bielefeld");
        d.setDatum(localDate);
        d.setBeschreibung("17 Ziegelsteine");

        e.setName("E_Hans");
        e.setOrt("Berlin");
        e.setDatum(localDate);
        e.setBeschreibung("17 Haustiere");

        dieSicht.add(a);
        dieSicht.add(b);
        dieSicht.add(c);
        dieSicht.add(d);
        dieSicht.add(e);

        m.addAttribute("willkommenUser", "Willkommen " + loggedinusername);
        m.addAttribute("meinesichtungen", dieSicht);
        m.addAttribute("headers", HEADERS);
    }

    @GetMapping("/meine")
    public String sichtungMeineGet(Model m, @ModelAttribute("loggedinusername") String loggedinusername) {
        m.addAttribute("willkommenUser", "Willkommen " + loggedinusername);
        m.addAttribute("headers", HEADERS);
        return "sichtung/meine/liste";
    }

    @GetMapping("/meine/neu")
    public String sichtungMeineNeuGet(Model m) {
        m.addAttribute("meinesichtungform", new Sichtung());
        return "sichtung/meine/bearbeiten";
    }

    @PostMapping("/meine/neu")
    public String sichtungMeineNeuPost(@Valid @ModelAttribute("meinesichtungform") Sichtung sichtung,
            BindingResult neueSichtungError, Model m, @ModelAttribute("meinesichtungen") List<Sichtung> dieSichtungen) {

        if (neueSichtungError.hasErrors()) {
            return "sichtung/meine/bearbeiten";
        }

        dieSichtungen.add(sichtung);
        return "redirect:/sichtung/meine";
    }

    @GetMapping("/meine/{index}")
    public String editGet(@ModelAttribute("meinesichtungform") Sichtung sichtung, Model m,
            @ModelAttribute("meinesichtungen") List<Sichtung> dieSichtungen, @PathVariable int index) {

        Sichtung aktSichtung = dieSichtungen.get(0);
        m.addAttribute("meinesichtungform", aktSichtung);
        logger.warn("/mein/index aktuelleSichtung: {}", aktSichtung.getName());
        dieSichtungen.remove(index);
        return "redirect:/sichtung/meine/bearbeiten";
    }

    @GetMapping("/meine/{index}/del")
    public String deleteGet(@ModelAttribute("meinesichtungen") List<Sichtung> dieSichtungen, @PathVariable int index) {
        dieSichtungen.remove(index);
        return "redirect:/sichtung/meine";
    }

    @GetMapping("/logout")
    public String logout(SessionStatus sessionstatus) {
        // Session aktiv beenden
        // Eigentlich unnötig, aber VS Code meckert rum
        sessionstatus.setComplete();
        return "login";
    }
}
