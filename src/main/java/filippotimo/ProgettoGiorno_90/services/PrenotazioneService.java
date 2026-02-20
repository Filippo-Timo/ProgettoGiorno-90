package filippotimo.ProgettoGiorno_90.services;

import filippotimo.ProgettoGiorno_90.entities.Evento;
import filippotimo.ProgettoGiorno_90.entities.Prenotazione;
import filippotimo.ProgettoGiorno_90.entities.Utente;
import filippotimo.ProgettoGiorno_90.exceptions.BadRequestException;
import filippotimo.ProgettoGiorno_90.exceptions.NotFoundException;
import filippotimo.ProgettoGiorno_90.payloads.PrenotazioneDTO;
import filippotimo.ProgettoGiorno_90.repositories.EventiRepository;
import filippotimo.ProgettoGiorno_90.repositories.PrenotazioniRepository;
import filippotimo.ProgettoGiorno_90.repositories.UtentiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PrenotazioneService {

    private final PrenotazioniRepository prenotazioniRepository;
    private final UtentiRepository utentiRepository;
    private final EventiRepository eventiRepository;

    @Autowired
    public PrenotazioneService(PrenotazioniRepository prenotazioniRepository, UtentiRepository utentiRepository, EventiRepository eventiRepository) {
        this.prenotazioniRepository = prenotazioniRepository;
        this.utentiRepository = utentiRepository;
        this.eventiRepository = eventiRepository;
    }


    // 1. GET -> Torna una lista di Prenotazioni

    public Page<Prenotazione> findAllPrenotazioni(int page, int size, String orderBy, String sortCriteria) {
        if (size > 100 || size < 0) size = 10;
        if (page < 0) page = 0;
        Pageable pageable = PageRequest.of(page, size, sortCriteria.equals("desc") ? Sort.by(orderBy).descending() : Sort.by(orderBy));
        return this.prenotazioniRepository.findAll(pageable);
    }


    // 2. GET -> Torna una singola Prenotazione specifica

    public Prenotazione findPrenotazioneById(Long prenotazioneId) {
        return this.prenotazioniRepository.findById(prenotazioneId).orElseThrow(() -> new NotFoundException(prenotazioneId));
    }


    // 3. POST -> Crea una Prenotazione

    public Prenotazione savePrenotazione(PrenotazioneDTO prenotazioneDTO) {

        Utente utente = utentiRepository.findById(prenotazioneDTO.utenteId()).orElseThrow(() -> new NotFoundException(prenotazioneDTO.utenteId()));
        Evento evento = eventiRepository.findById(prenotazioneDTO.eventoId()).orElseThrow(() -> new NotFoundException(prenotazioneDTO.eventoId()));

        if (prenotazioniRepository.existsByUtenteIdAndEventoId(utente.getId(), evento.getId()))
            throw new BadRequestException("Esiste già un viaggio programmato per questo utente in questa data");

        Prenotazione newPrenotazione = new Prenotazione(
                utente,
                evento
        );

        Prenotazione savedPrenotazione = this.prenotazioniRepository.save(newPrenotazione);

        System.out.println("La prenotazione con id: " + savedPrenotazione.getId() + " è stata aggiunta correttamente al DB!");

        return savedPrenotazione;
    }

}
