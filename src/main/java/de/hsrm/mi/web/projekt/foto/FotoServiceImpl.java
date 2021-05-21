package de.hsrm.mi.web.projekt.foto;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.hsrm.mi.web.projekt.utils.FotoBearbeitungService;

@Service
public class FotoServiceImpl implements FotoService {
    Logger logger = LoggerFactory.getLogger(FotoServiceImpl.class);

    @Autowired
    private FotoBearbeitungService fbservice;
    @Autowired
    private FotoRepository fotoRepository;

    @Autowired
    private AdressService adressservice;

    @Override
    public Foto fotoAbspeichern(Foto foto) {
        fbservice.aktualisiereMetadaten(foto);
        fbservice.orientiereFoto(foto);
        
        Optional<String> ortString = adressservice.findeAdresse(foto.getGeobreite(), foto.getGeolaenge());
        if(ortString.isPresent()){
            foto.setOrt(ortString.get());
        }
        return fotoRepository.save(foto);
    }

    @Override
    public Optional<Foto> fotoAbfragenNachId(Long id) {
        return fotoRepository.findById(id);
    }

    @Override
    public List<Foto> alleFotosNachZeitstempelSortiert() {
        return fotoRepository.findAllByOrderByZeitstempelAsc();
    }

    @Override
    public void loescheFoto(Long id) {
        fotoRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void fotoKommentieren(long id, String autor, String kommentar) throws NoSuchElementException {
        // TODO Irgendwie hässlich. Bitte morgen aufräumen. Ist jetzt zu spät geworden.
        Optional<Foto> fotoopt = fotoRepository.findById(id);
        var foto = fotoopt.orElseThrow();
        var k = new Kommentar();
        k.setAutor(autor);
        k.setText(kommentar);
        List<Kommentar> kommentare = foto.getKommentare();
        kommentare.add(k);
        foto.setKommentare(kommentare);
    }

    @Override
    @Transactional
    public List<Kommentar> alleKommentareFuerFoto(long fotoid) throws NoSuchElementException {
        // TODO Bitte morgen aufräumen. Ist jetzt zu spät geworden.

        Optional<Foto> fotoopt = fotoRepository.findById(fotoid);
        var foto = fotoopt.orElseThrow();

        List<Kommentar> kommentare = foto.getKommentare();

        return kommentare;
    }

    @Override
    @Transactional
    public void fotoKommentarLoeschen(long fotoid, long kid) throws NoSuchElementException {
        // TODO Hässlicher Code, vorallem unten mit der for-Schleife und dem throw new. Bitte aufräumen

        Optional<Foto> fotoopt = fotoRepository.findById(fotoid);
        Foto foto = fotoopt.orElseThrow();
        // Kommentar kommentar = foto.getKommentare().get(kid);

        List<Kommentar> kommentare = foto.getKommentare();
        // kommentare.remove(kommentar);

        
        for(Kommentar kommentar : kommentare) {
            // if(kommentar.getId() == kid) {
            if(kommentar.getId() == kid ) {
                kommentare.remove(kommentar);
                return;
            }
        }
        throw new NoSuchElementException();

    }
    
}
