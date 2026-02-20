package filippotimo.ProgettoGiorno_90.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UtenteDTO(
        @NotBlank(message = "Il nome dell'utente è obbligatorio")
        @Size(min = 2, max = 30, message = "Il nome deve contenere tra i 2 e i 30 caratteri")
        String nome,
        @NotBlank(message = "Il cognome dell'utente è obbligatorio")
        @Size(min = 2, max = 30, message = "Il cognome deve contenere tra i 2 e i 30 caratteri")
        String cognome,
        @NotBlank(message = "L'indirizzo email dell'utente è obbligatorio")
        @Email(message = "L'indirizzo email inserito non è nel formato corretto!")
        String email,
        @NotBlank(message = "La password è obbligatoria")
        @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{4,}$", message = "La password deve contenere una maiuscola, una minuscola ecc ecc ...")
        String password) {

}


