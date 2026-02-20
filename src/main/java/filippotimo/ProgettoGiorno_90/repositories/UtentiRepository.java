package filippotimo.ProgettoGiorno_90.repositories;

import filippotimo.ProgettoGiorno_90.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtentiRepository extends JpaRepository<Utente, Long> {

    Optional<Utente> findByEmail(String email);

}
