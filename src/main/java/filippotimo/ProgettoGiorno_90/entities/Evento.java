package filippotimo.ProgettoGiorno_90.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "Eventi")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    private String titolo;

    private String descrizione;

    private LocalDate data;

    private String luogo;

    private int numeroPostiDisponibili;

    @ManyToOne
    @JoinColumn(name = "organizzatore_id")
    private Utente organizzatore;

    public Evento(String titolo, String descrizione, LocalDate data, String luogo, int numeroPostiDisponibili, Utente organizzatore) {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.data = data;
        this.luogo = luogo;
        this.numeroPostiDisponibili = numeroPostiDisponibili;
        this.organizzatore = organizzatore;
    }

}
