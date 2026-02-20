package filippotimo.ProgettoGiorno_90.payloads;

import filippotimo.ProgettoGiorno_90.entities.Utente;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record EventoDTO(
        @NotBlank(message = "Il titolo dell'evento è obbligatorio")
        @Size(min = 2, max = 30, message = "Il titolo deve contenere tra i 2 e i 30 caratteri")
        String titolo,
        @NotBlank(message = "La descrizione dell'evento è obbligatoria")
        @Size(min = 2, max = 100, message = "La descrizione deve contenere tra i 2 e i 100 caratteri")
        String descrizione,
        @NotNull(message = "La data dell'evento è obbligatoria")
        @FutureOrPresent(message = "La data non può essere nel passato")
        LocalDate data,
        @NotBlank(message = "Il luogo dell'evento è obbligatoria")
        @Size(min = 2, max = 30, message = "Il luogo dell'evento deve contenere tra i 2 e i 30 caratteri")
        String luogo,
        @NotNull(message = "Il numero di posti diponibili dell'evento è obbligatoria")
        int numeroPostiDisponibili,
        @NotNull(message = "L'ID dell'organizzatore è obbligatorio")
        Utente organizzatoreId
) {
}
