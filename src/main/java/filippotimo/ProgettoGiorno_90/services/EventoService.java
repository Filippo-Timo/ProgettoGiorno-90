package filippotimo.ProgettoGiorno_90.services;

import filippotimo.ProgettoGiorno_90.entities.Evento;
import filippotimo.ProgettoGiorno_90.entities.Utente;
import filippotimo.ProgettoGiorno_90.exceptions.NotFoundException;
import filippotimo.ProgettoGiorno_90.exceptions.UnauthorizedException;
import filippotimo.ProgettoGiorno_90.payloads.EventoDTO;
import filippotimo.ProgettoGiorno_90.repositories.EventiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class EventoService {

    private final EventiRepository eventiRepository;

    @Autowired
    public EventoService(EventiRepository eventiRepository) {
        this.eventiRepository = eventiRepository;
    }


    // 1. GET -> Torna una pagina di un numero definito di Eventi

    public Page<Evento> findAllEventi(int page, int size, String orderBy, String sortCriteria) {
        if (size > 100 || size < 0) size = 10;
        if (page < 0) page = 0;
        Pageable pageable = PageRequest.of(page, size, sortCriteria.equals("desc") ? Sort.by(orderBy).descending() : Sort.by(orderBy));
        return this.eventiRepository.findAll(pageable);
    }


    // 2. GET -> Torna un singolo Evento specifico

    public Evento findEventoById(Long eventoId) {
        return this.eventiRepository.findById(eventoId).orElseThrow(() -> new NotFoundException(eventoId));
    }


    // 3. POST -> Crea un Evento

    public Evento saveEvento(EventoDTO eventoDTO, Utente organizzatore) {

        Evento newEvento = new Evento(
                eventoDTO.titolo(),
                eventoDTO.descrizione(),
                eventoDTO.data(),
                eventoDTO.luogo(),
                eventoDTO.numeroPostiDisponibili(),
                organizzatore
        );

        Evento savedEvento = this.eventiRepository.save(newEvento);

        System.out.println("L'evento con titolo " + savedEvento.getTitolo() + " è stato aggiunto correttamente al DB!");

        return savedEvento;
    }


    // 4. PUT -> Modifica lo specifico Evento

    public Evento findByIdAndUpdateEvento(Long eventoId, EventoDTO eventoDTO, Utente organizzatore) {
        Evento found = this.findEventoById(eventoId);

        if (found.getOrganizzatore().getId().equals(organizzatore.getId())) {

            found.setTitolo(eventoDTO.titolo());
            found.setDescrizione(eventoDTO.descrizione());
            found.setData(eventoDTO.data());
            found.setLuogo(eventoDTO.luogo());
            found.setNumeroPostiDisponibili(eventoDTO.numeroPostiDisponibili());

            Evento modifiedEvento = this.eventiRepository.save(found);

            System.out.println("L'evento con titolo " + modifiedEvento.getTitolo() + " è stato modificato correttamente!");

            return modifiedEvento;
        } else {
            throw new UnauthorizedException("Solo l'organizzatore proprietario di questo evento può modificarlo");
        }
    }


    // 5. DELETE -> Cancella lo specifico Evento

    public void findByIdAdDeleteEvento(Long eventoId, Utente organizzatore) {
        Evento found = this.findEventoById(eventoId);
        if (found.getOrganizzatore().getId().equals(organizzatore.getId())) {
            this.eventiRepository.delete(found);
        } else {
            throw new UnauthorizedException("Solo l'organizzatore proprietario di questo evento può cancellarlo");
        }
    }

}
