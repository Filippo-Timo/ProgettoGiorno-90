package filippotimo.ProgettoGiorno_90.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Utenti")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    private String nome;

    private String cognome;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Ruolo ruolo;

    public Utente(String nome, String cognome, String email, String password) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.ruolo = Ruolo.UTENTE;
    }

}
