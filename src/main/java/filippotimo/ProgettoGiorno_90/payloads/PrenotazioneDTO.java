package filippotimo.ProgettoGiorno_90.payloads;

import filippotimo.ProgettoGiorno_90.entities.Evento;
import filippotimo.ProgettoGiorno_90.entities.Utente;
import jakarta.validation.constraints.NotNull;

public record PrenotazioneDTO(
        @NotNull(message = "L'ID dell'utente è obbligatorio")
        Utente utenteId,
        @NotNull(message = "L'ID dell'evento è obbligatorio")
        Evento eventoId
) {
}
