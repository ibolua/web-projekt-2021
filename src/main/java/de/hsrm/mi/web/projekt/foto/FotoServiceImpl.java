package de.hsrm.mi.web.projekt.foto;

import java.util.List;
import java.util.Optional;

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
    
}
