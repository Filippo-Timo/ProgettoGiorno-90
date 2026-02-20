package filippotimo.ProgettoGiorno_90.payloads;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorsWithListDTO(String message, LocalDateTime timestamp, List<String> errors) {
}
