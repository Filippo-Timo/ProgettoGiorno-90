package filippotimo.ProgettoGiorno_90.repositories;

import filippotimo.ProgettoGiorno_90.entities.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrenotazioniRepository extends JpaRepository<Prenotazione, Long> {

    boolean existsByUtenteIdAndEventoId(Long utenteId, Long eventoId);

}
