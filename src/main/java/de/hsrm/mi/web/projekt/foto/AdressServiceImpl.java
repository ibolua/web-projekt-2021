package de.hsrm.mi.web.projekt.foto;

import java.net.URI;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AdressServiceImpl implements AdressService {

    @Override
    public Optional<String> findeAdresse(double geobreite, double geolaenge) {
        Logger logger = LoggerFactory.getLogger(AdressServiceImpl.class);
        // https://nominatim.openstreetmap.org/reverse?lat=<value>&lon=<value>&<params>
        // https://nominatim.openstreetmap.org/reverse?lat=50.09729&lon=8.21704&format=json

        final URI URL = URI.create(
                "https://nominatim.openstreetmap.org/reverse?lat=" + geobreite + "&lon=" + geolaenge + "&format=json");
        logger.warn("REST-URL: {}", URL);

        var resttemplate = new RestTemplate();
        var result = resttemplate.getForObject(URL, Adresse.class);

        try {
            return Optional.of(result.getDisplay_name());
        } catch (Exception e) {

            return Optional.empty();
        }

    }

}
