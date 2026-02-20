package filippotimo.ProgettoGiorno_90.security;

import filippotimo.ProgettoGiorno_90.entities.Utente;
import filippotimo.ProgettoGiorno_90.exceptions.UnauthorizedException;
import filippotimo.ProgettoGiorno_90.services.UtenteService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTCheckerFilter extends OncePerRequestFilter {

    private final JWTTools jwtTools;
    private final UtenteService utenteService;

    @Autowired
    public JWTCheckerFilter(JWTTools jwtTools, UtenteService utenteService) {

        this.jwtTools = jwtTools;
        this.utenteService = utenteService;
    }

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // All'inizio faccio dei System.out per vedere se raggiungo correttamente questo punto
        System.out.println("Ci troviamo dentro al filtro custom");
        System.out.println(request.getMethod());


        // *************************** AUTENTICAZIONE ***************************

        // TODO: 1) Verifico se la richiesta contiene l'Header Authorization nel formato giusto

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer "))
            throw new UnauthorizedException("Il Token non è presente nell'Authorization Header oppure è in un formato errato");

        // TODO: 2) Estraggo il Token dall'Header

        String accessToken = authHeader.replace("Bearer ", "");

        // TODO: 3) Verifico se il Token è valido

        jwtTools.verifyToken(accessToken);


        // *************************** AUTORIZZAZIONE ***************************

        // TODO: 1) Cerco l'utente nel DB tramite id che si trova nel token
        // TODO: 1.1) Leggo l'id dal token
        long utenteId = jwtTools.extractIdFromToken(accessToken);

        // TODO: 1.2) Recupero l'utente con una findUtenteById
        Utente authenticatedUtente = this.utenteService.findUtenteById(utenteId);

        // TODO: 2) Associo l'utente al Security Context

        Authentication authentication = new UsernamePasswordAuthenticationToken(authenticatedUtente, null, authenticatedUtente.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // TODO: 3) Se tutto è andato a buon fine passo al prossimo elemento della catena

        filterChain.doFilter(request, response);

    }
}
