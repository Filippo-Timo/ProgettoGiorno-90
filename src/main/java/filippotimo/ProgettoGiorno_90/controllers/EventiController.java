package filippotimo.ProgettoGiorno_90.controllers;

import filippotimo.ProgettoGiorno_90.entities.Evento;
import filippotimo.ProgettoGiorno_90.entities.Utente;
import filippotimo.ProgettoGiorno_90.exceptions.ValidationException;
import filippotimo.ProgettoGiorno_90.payloads.EventoDTO;
import filippotimo.ProgettoGiorno_90.services.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eventi")
public class EventiController {

    private final EventoService eventoService;

    @Autowired
    public EventiController(EventoService eventoService) {

        this.eventoService = eventoService;
    }


    // 1. GET URL/eventi -> Ritorna la lista di tutti gli eventi divisa in pagine

    @GetMapping
    public Page<Evento> findAllEventi(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "3") int size,
                                      @RequestParam(defaultValue = "luogo") String orderBy,
                                      @RequestParam(defaultValue = "asc") String sortCriteria) {
        return this.eventoService.findAllEventi(page, size, orderBy, sortCriteria);
    }

    // 2. GET URL/eventi/1 -> Ritorna un singolo evento

    @GetMapping("/{eventoId}")
    public Evento findEventoById(@PathVariable Long eventoId) {
        return this.eventoService.findEventoById(eventoId);
    }

    // 3. POST URL/eventi -> Crea un nuovo evento

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ORGANIZZATORE')")
    @ResponseStatus(HttpStatus.CREATED)
    public Evento createEvento(@AuthenticationPrincipal Utente organizzatore,
                               @RequestBody @Validated EventoDTO eventoDTO,
                               BindingResult validationResult) {

        if (validationResult.hasErrors()) {
            List<String> errorsList = validationResult.getFieldErrors()
                    .stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();

            throw new ValidationException(errorsList);

        } else {
            return this.eventoService.saveEvento(eventoDTO, organizzatore);
        }
    }


    // 4. PUT URL/eventi/1 -> Modifica lo specifico evento

    @PutMapping("/{eventoId}")
    public Evento findViaggioByIdAndUpdate(@AuthenticationPrincipal Utente organizzatore,
                                           @PathVariable Long eventoId,
                                           @RequestBody @Validated EventoDTO eventoDTO,
                                           BindingResult validationResult) {

        if (validationResult.hasErrors()) {

            List<String> errorsList = validationResult.getFieldErrors()
                    .stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();

            throw new ValidationException(errorsList);

        } else {
            return this.eventoService.findByIdAndUpdateEvento(eventoId, eventoDTO, organizzatore);
        }
    }

    // 5. DELETE URL/eventi/1 -> Cancella lo specifico evento

    @DeleteMapping("/{eventoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findViaggioByIdAndDelete(
            @AuthenticationPrincipal Utente organizzatore,
            @PathVariable Long eventoId) {

        this.eventoService.findByIdAdDeleteEvento(eventoId, organizzatore);
    }


}
