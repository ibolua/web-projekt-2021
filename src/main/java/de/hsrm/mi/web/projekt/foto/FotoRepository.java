package de.hsrm.mi.web.projekt.foto;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FotoRepository extends JpaRepository<Foto, Long>{

    // Sucht das Foto mit der angegebenen id. Ergebnis als Optional<Foto>.
    Optional<Foto> findById(Long id);

    // Alle Fotos nach Zeitstempel sortiert als Liste.
    List<Foto> findAllByOrderByZeitstempelAsc();

    // löscht das Foto mit der ID id aus dem epository.
    void deleteById(Long id);
    
}
