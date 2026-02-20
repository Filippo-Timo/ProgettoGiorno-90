package filippotimo.ProgettoGiorno_90.services;

import filippotimo.ProgettoGiorno_90.entities.Utente;
import filippotimo.ProgettoGiorno_90.exceptions.UnauthorizedException;
import filippotimo.ProgettoGiorno_90.payloads.LoginDTO;
import filippotimo.ProgettoGiorno_90.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UtenteService utenteService;
    private final JWTTools jwtTools;
    private final PasswordEncoder bcrypt;

    @Autowired
    public AuthService(UtenteService utenteService, JWTTools jwtTools, PasswordEncoder passwordEncoder) {
        this.utenteService = utenteService;
        this.jwtTools = jwtTools;
        this.bcrypt = passwordEncoder;
    }


    public String checkCredentialsAndGenerateToken(LoginDTO body) {

        // TODO: 1) Controllo sulle credenziali
        // 1.1) Controllo se esiste un utente con l'indirizzo email inserito
        Utente found = this.utenteService.findUtenteByEmail(body.email());

        // 1.2) Se esiste controllo che la sua password è uguale a quella nel body
        if (bcrypt.matches(body.password(), found.getPassword())) {

            // Se supera i primi due punti allora si passa al secondo in cui:
            // TODO: 2) Creo Token
            String accessToken = jwtTools.generateToken(found);

            // 2.1) Ritorno il Token
            return accessToken;

        } else {

            // Qui gestisco eventuali errori lanciando un 401 (Unauthorized)
            throw new UnauthorizedException("Le credenziali inserite sono errate!");

        }
    }

}
