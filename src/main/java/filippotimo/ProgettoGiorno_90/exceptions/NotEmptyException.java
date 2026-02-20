package filippotimo.ProgettoGiorno_90.exceptions;

public class NotEmptyException extends RuntimeException {
    public NotEmptyException() {
        super("Il file non puo' essere vuoto!");
    }
}
