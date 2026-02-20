package filippotimo.ProgettoGiorno_90.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {

        // TODO: 1) RIMUOVO IL LOGIN CHE C'E' DI DEFAULT

        httpSecurity.formLogin(formLogin -> formLogin.disable());

        // TODO: 2) RIMUOVO IL CSRF

        httpSecurity.csrf(csrf -> csrf.disable());

        // TODO: 3) CAMBIO IL MECCANISMO A SESSIONI

        httpSecurity.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // TODO: 4) DISABILITO LA PROTEZIONE SU TUTTI GLI ENDPOINT

        httpSecurity.authorizeHttpRequests(req -> req.requestMatchers("/**").permitAll());

        // TODO: 5) INFINE CREIAMO L'OGGETTO CHE SERVE A SPRING SECURITY PER APPLICARE QUESTE IMPOSTAZIONI

        return httpSecurity.build();

    }
    

// ******************************** Bcrypt ********************************
//  Tramite questo @Bean configuro BCrypt per la protezione delle password

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(14);
    }

}
