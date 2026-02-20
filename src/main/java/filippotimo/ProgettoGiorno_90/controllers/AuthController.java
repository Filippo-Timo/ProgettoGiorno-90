package filippotimo.ProgettoGiorno_90.controllers;

import filippotimo.ProgettoGiorno_90.entities.Utente;
import filippotimo.ProgettoGiorno_90.exceptions.ValidationException;
import filippotimo.ProgettoGiorno_90.payloads.LoginDTO;
import filippotimo.ProgettoGiorno_90.payloads.LoginResponseDTO;
import filippotimo.ProgettoGiorno_90.payloads.UtenteDTO;
import filippotimo.ProgettoGiorno_90.services.AuthService;
import filippotimo.ProgettoGiorno_90.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UtenteService utenteService;

    @Autowired
    public AuthController(AuthService authService, UtenteService utenteService) {
        this.authService = authService;
        this.utenteService = utenteService;
    }

    // 1. POST URL/auth/login -> Effettua il login per ottenere il Token

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginDTO body) {

        return new LoginResponseDTO(this.authService.checkCredentialsAndGenerateToken(body));

    }


    // 2. POST URL/auth/register -> Crea un nuovo Utente

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Utente createDipendente(@RequestBody @Validated UtenteDTO utenteDTO, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errorsList = validationResult.getFieldErrors()
                    .stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();

            throw new ValidationException(errorsList);
        } else {
            return this.utenteService.saveUtente(utenteDTO);
        }
    }

}
