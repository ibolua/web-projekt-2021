package de.hsrm.mi.web.projekt.foto;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FotoController {
    private static final int MINDESTDATEIGROESSE = 17;
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
        return "foto/list";
    }

    @GetMapping("/foto")
    public String fotoGet(Model m) {
        m.addAttribute("fotos", fotoservice.alleFotosNachZeitstempelSortiert());
        return "foto/list";
    }

    @GetMapping("/foto/{id}")
    public ResponseEntity<byte[]> fotoGetId(@PathVariable Long id) {
        Optional<Foto> fotoOptional = fotoservice.fotoAbfragenNachId(id);

        if (fotoOptional.isPresent()) {
            Foto foto = fotoOptional.get();
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

}
