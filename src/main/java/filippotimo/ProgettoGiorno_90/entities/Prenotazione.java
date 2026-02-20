package filippotimo.ProgettoGiorno_90.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Table(name = "Prenotazioni")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Prenotazione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "utente_id")
    private Utente utente;

    @ManyToOne
    @JoinColumn(name = "evento_id")
    private Evento evento;

    private LocalDate dataDiPrenotazione;

    public Prenotazione(Utente utente, Evento evento, LocalDate dataDiPrenotazione) {
        this.utente = utente;
        this.evento = evento;
        this.dataDiPrenotazione = dataDiPrenotazione;
    }
    
}
