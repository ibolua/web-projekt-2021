package de.hsrm.mi.web.projekt.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class JwtLoginController {
    Logger logger = LoggerFactory.getLogger(JwtLoginController.class);

    /*
     * AuthenticationManager ermöglicht Login-Überprüfung gegen alle konfigurierten
     * Authentifikations-Quellen (siehe Security-Konfiguration). Man könnte auch den
     * eigenen UserDetailService verwenden, dann wären aber "nur" die Datenbank-User
     * abgedeckt, nicht z.B. die "in memory" angelegten
     */
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    /*
     * Test aus Kommandozeile z.B. mit folgendem Kommando möglich
     * curl -v -X POST -H "Content-Type: application/json" -d '{ "username":"joghurta", "password":"geheim123" }' http://localhost:9090/api/login
     */
    @PostMapping("/api/login")
    public String get_jwt_token(@RequestBody JwtLoginRequest logindata) {
        logger.warn("GET_JWT_TOKEN: {}", logindata);
        String token=null;
        Authentication auth = null;
        try {
            // AuthenticationManager zur Überprüfung der übergebenen Login-Daten verwenden,
            // würde eine Authentication(Sub-)Exception werfen, falls das fehlschlüge
            auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(logindata.getUsername(), logindata.getPassword()));
            logger.info("get_jwt_token({}) -> isAuthenticated {}", logindata, auth);

            // (Ersten) Authority-Eintrag ermitteln und daraus Rollen-Namen basteln
            var authority = auth.getAuthorities().stream().findFirst().orElseThrow();
            var rollenname = authority.getAuthority().substring("ROLE_".length());
            logger.info("get_jwt_token({}) Authority {} -> rollenname {}", logindata, authority, rollenname);

            // JWT mit Claims zu Benutzernamen und Rolle basteln
            token = jwtUtil.bastelJwtToken(logindata.getUsername(), rollenname);

        } catch (AuthenticationException e) {
            // Authentifizierung fehlgeschlagen, HTTP-Status für Client auf 401 (unauthorized) setzen
            logger.error("get_jwt_token({}) ERROR, NOT isAuthenticated; {}", logindata, auth, e.getMessage());
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, logindata.getUsername());
        }
        // Authentifizierung erfolgreich - JWT als einfachen String an Client zurückgeben
        return token;
    }
}
