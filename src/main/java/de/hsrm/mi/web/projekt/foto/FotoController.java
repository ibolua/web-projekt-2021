package de.hsrm.mi.web.projekt.foto;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

@Controller
// @SessionAttributes(names = { "loggedinusername" })
public class FotoController {
    private static final int MINDESTDATEIGROESSE = 17;
    private static final String FOTO_LOGIN_STRING = "foto/login"; // Compliant
    private static final String FOTO_LIST_STRING = "foto/list"; // Compliant
    private static final String LOGGEDINUSERNAME_STRING = "loggedinusername"; // Compliant
    Logger logger = LoggerFactory.getLogger(FotoController.class);
    @Autowired
    FotoService fotoservice;

    @PostMapping("/foto")
    public String fotoPost(MultipartFile datei, Model m) {
        // var, weil: Local-Variable Type Inference should be used(java:S6212)(Seit Java 10)
        var foto = new Foto();

        if (datei.getSize() >= MINDESTDATEIGROESSE) {
            foto.setDateiname(datei.getOriginalFilename());
            try {
                foto.setFotodaten(datei.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            foto.setMimetype(datei.getContentType());

            fotoservice.fotoAbspeichern(foto);
        }

        m.addAttribute("fotos", fotoservice.alleFotosNachZeitstempelSortiert());
        return FOTO_LIST_STRING;
    }

    @GetMapping("/foto")
    public String fotoGet(Model m) {
        m.addAttribute("fotos", fotoservice.alleFotosNachZeitstempelSortiert());
        return FOTO_LIST_STRING;
    }

    @GetMapping("/foto/{id}")
    public ResponseEntity<byte[]> fotoGetId(@PathVariable Long id) {
        Optional<Foto> fotoOptional = fotoservice.fotoAbfragenNachId(id);

        if (fotoOptional.isPresent()) {
            var foto = fotoOptional.get();
            return ResponseEntity.ok()
                        .header("Content-Type", foto.getMimetype())
                        .body(foto.getFotodaten());
        }
        return null;
    }

    @GetMapping("foto/{id}/del")
    public String fotoGetIdDelete(@PathVariable Long id) {
        fotoservice.loescheFoto(id);
        return "redirect:/foto";
    }

    @GetMapping("foto/{id}/kommentar")
    public String fotoKommentarGet(@PathVariable Long id, Model m,
            // @SessionAttribute("loggedinusername") String loggedinusername)
            @ModelAttribute(LOGGEDINUSERNAME_STRING) String loggedinusername)
            {
        Optional<Foto> foto = fotoservice.fotoAbfragenNachId(id);
              
        // logger.warn("GET Kommentar LOGGEDINUSERNAME SessionAttribute: {}", loggedinusername);
        logger.warn("GET Kommentar LOGGEDINUSERNAME ModelAttribute: {}", loggedinusername);

        if (foto.isPresent()) {
            m.addAttribute("foto", foto.get());
            return "foto/kommentare";
        }
        return FOTO_LIST_STRING;
    }

    @PostMapping("foto/{id}/kommentar")
    public String fotoKommentarPost(@PathVariable Long id,
            // @SessionAttribute("loggedinusername") String loggedinusername,
            @ModelAttribute(LOGGEDINUSERNAME_STRING) String loggedinusername,
            @RequestParam String kommentar,
            Model m,
            HttpSession session) {
        logger.warn("POST Kommentar ID: {}", id);
        logger.warn("POST Kommentar LOGGEDINUSERNAME: {}", loggedinusername);

        var test = m.getAttribute(LOGGEDINUSERNAME_STRING).toString();
        logger.warn("TEST Kommentar LOGGEDINUSERNAME: {}", test);


        // VERSUCH MIT HttpSession
        var loggedinusernameSessionObject = session.getAttribute(LOGGEDINUSERNAME_STRING);
        String loggedinusernameSTRING;
        if(loggedinusernameSessionObject != null) {
            loggedinusernameSTRING = loggedinusernameSessionObject.toString();
            logger.error("/FOTO LOGGEDINUSERNAME: {}", loggedinusernameSTRING);
        }
        logger.error("/FOTO Kommentar sessionObject: {}", loggedinusernameSessionObject);
        // VERSUCH MIT HttpSession

        // if (isNotBlankOrIsNotEmpty(kommentar) && (isNotBlankOrIsNotEmpty(loggedinusername))) {
        if (isNotBlankOrIsNotEmpty(kommentar) && loggedinusernameSessionObject != null) {
            loggedinusernameSTRING = loggedinusernameSessionObject.toString();
            fotoservice.fotoKommentieren(id, loggedinusernameSTRING, kommentar);
            // fotoservice.fotoKommentieren(id, loggedinusername, kommentar);
        }
        return "redirect:/foto/" + id + "/kommentar";
    }

    private static boolean isNotBlankOrIsNotEmpty(String s) {
        return !(s.isBlank() || s.isEmpty());
    }

    @GetMapping("/logoutfoto2")
    public String logout(SessionStatus sessionstatus) {
        // Session aktiv beenden
        sessionstatus.setComplete();
        return FOTO_LOGIN_STRING;
    }
}
