package de.hsrm.mi.web.projekt.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.hsrm.mi.web.projekt.foto.Foto;
import de.hsrm.mi.web.projekt.foto.FotoService;
import de.hsrm.mi.web.projekt.foto.Kommentar;

@RestController
@RequestMapping("/api/foto")
public class FotoRestController {

    @Autowired
    FotoService fotoservice;

    Foto foto;

    @GetMapping("")
    public List<Foto> getApiFoto() {
        return fotoservice.alleFotosNachZeitstempelSortiert();
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getApiFotoId(@PathVariable long id) {
        Optional<Foto> fotoOptional = fotoservice.fotoAbfragenNachId(id);

        if (fotoOptional.isPresent()) {
            foto = fotoOptional.get();
            return ResponseEntity.ok()
                        .header("Content-Type", foto.getMimetype())
                        .body(foto.getFotodaten());
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteApiFoto(@PathVariable long id) {
        fotoservice.loescheFoto(id);
    }

    @GetMapping("/{id}/kommentar")
    public List<Kommentar> getApiFotoIdKommentar(@PathVariable long id) {
        return fotoservice.alleKommentareFuerFoto(id);
    }

    @DeleteMapping("/{id}/kommentar/{kid}")
    public void deleteApiFotoIdKommentarKid(@PathVariable long id, @PathVariable long kid) {
        fotoservice.fotoKommentarLoeschen(id, kid);
    }

}
