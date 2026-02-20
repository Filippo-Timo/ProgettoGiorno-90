package filippotimo.ProgettoGiorno_90.services;

import filippotimo.ProgettoGiorno_90.entities.Utente;
import filippotimo.ProgettoGiorno_90.exceptions.BadRequestException;
import filippotimo.ProgettoGiorno_90.exceptions.NotFoundException;
import filippotimo.ProgettoGiorno_90.payloads.UtenteDTO;
import filippotimo.ProgettoGiorno_90.repositories.UtentiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UtenteService {

    private final UtentiRepository utentiRepository;
    private final PasswordEncoder bcrypt;

    @Autowired
    public UtenteService(UtentiRepository utentiRepository, PasswordEncoder passwordEncoder) {
        this.utentiRepository = utentiRepository;
        this.bcrypt = passwordEncoder;
    }


    // 1. GET -> Torna una pagina di un numero definito di Utenti

    public Page<Utente> findAllUtenti(int page, int size, String orderBy, String sortCriteria) {
        if (size > 100 || size < 0) size = 10;
        if (page < 0) page = 0;
        Pageable pageable = PageRequest.of(page, size, sortCriteria.equals("desc") ? Sort.by(orderBy).descending() : Sort.by(orderBy));
        return this.utentiRepository.findAll(pageable);
    }


    // 2. GET -> Torna un singolo Utente specifico

    public Utente findUtenteById(Long utenteId) {
        return this.utentiRepository.findById(utenteId)
                .orElseThrow(() -> new NotFoundException(utenteId));
    }


    // 3. POST -> Crea un Utente

    public Utente saveUtente(UtenteDTO utenteDTO) {

        this.utentiRepository.findByEmail(utenteDTO.email()).ifPresent(utente -> {
            throw new BadRequestException("L'email " + utente.getEmail() + " è già in uso!");
        });

        Utente newUtente = new Utente(
                utenteDTO.nome(),
                utenteDTO.cognome(),
                utenteDTO.email(),
                bcrypt.encode(utenteDTO.password())
        );

        Utente savedUtente = this.utentiRepository.save(newUtente);

        System.out.println("L'utente " + savedUtente.getNome() + " " + savedUtente.getCognome() + " è stato aggiunto correttamente al DB!");

        return savedUtente;
    }


    // 4. GET -> Cerca Utente per email

    public Utente findUtenteByEmail(String email) {
        return this.utentiRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("L'utente con email: " + email + " non esiste!"));
    }

}
