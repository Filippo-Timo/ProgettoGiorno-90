package filippotimo.ProgettoGiorno_90.repositories;

import filippotimo.ProgettoGiorno_90.entities.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventiRepository extends JpaRepository<Evento, Long> {
}
