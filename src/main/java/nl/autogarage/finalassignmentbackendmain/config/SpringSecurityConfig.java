package nl.autogarage.finalassignmentbackendmain.config;

import nl.autogarage.finalassignmentbackendmain.filter.JwtRequestFilter;
import nl.autogarage.finalassignmentbackendmain.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


//Hier staan al je endpoints in
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    public final CustomUserDetailsService customUserDetailsService;
    private final JwtRequestFilter jwtRequestFilter;

    public SpringSecurityConfig(CustomUserDetailsService customUserDetailsService, JwtRequestFilter jwtRequestFilter) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    // PasswordEncoderBean. Deze kun je overal in je applicatie injecteren waar nodig.
    // Je kunt dit ook in een aparte configuratie klasse zetten.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    // Authenticatie met customUserDetailsService en passwordEncoder

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }


    // Authorizatie met jwt
    @Bean
    protected SecurityFilterChain filter(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .httpBasic().disable()
                .cors().and()
                .authorizeHttpRequests()
//                 Wanneer je deze regel hieronder   uncomment , staat de hele security open. Je hebt dan alleen nog een jwt nodig.
                .requestMatchers("/**").permitAll()
//              ENDPOINTS USERS
                .requestMatchers(HttpMethod.POST, "/users").permitAll()
//                Create users --> voor office en voor mechanic dit kan amdmin doen of kijken naar post van authorities
                .requestMatchers(HttpMethod.GET, "/users/{username}").hasAnyRole("ADMIN", "OFFICE")
                .requestMatchers(HttpMethod.PUT, "/users/{username}").hasAnyRole("ADMIN", "OFFICE")
                .requestMatchers(HttpMethod.DELETE, "/users/{username}").hasAnyRole("ADMIN", "OFFICE")
                .requestMatchers(HttpMethod.GET, "/users/{username}/authorities").hasAnyRole("ADMIN", "OFFICE")
                .requestMatchers(HttpMethod.POST, "/users/{username}/authorities").hasAnyRole("ADMIN", "OFFICE")
                .requestMatchers(HttpMethod.DELETE, "/users/{username}/authorities/{authority}").hasAnyRole("ADMIN", "OFFICE")

//                ENDPOINTS CAR
                .requestMatchers(HttpMethod.POST, "/car/add").hasRole("MECHANIC")

                .requestMatchers(HttpMethod.GET, "/car").hasAnyRole("ADMIN", "OFFICE", "MECHANIC")
                .requestMatchers(HttpMethod.GET, "/car/licenseplate").hasAnyRole("ADMIN", "OFFICE", "MECHANIC")
                .requestMatchers(HttpMethod.PUT, "/car/licenseplate").hasAnyRole("ADMIN", "MECHANIC")
                .requestMatchers(HttpMethod.DELETE, "/car/delete").hasAnyRole("ADMIN", "OFFICE", "MECHANIC")

//                ENDPOINTS INVOICE
                .requestMatchers(HttpMethod.POST, "/invoices").hasAnyRole("ADMIN", "OFFICE")
                .requestMatchers(HttpMethod.GET, "/invoice/").hasAnyRole("ADMIN", "OFFICE")
                .requestMatchers(HttpMethod.POST, "/invoice/add/{inspection_id}").hasAnyRole("ADMIN", "OFFICE", "MECHANIC")
                .requestMatchers(HttpMethod.GET, "/invoice/{id}").hasAnyRole("ADMIN", "OFFICE")
                .requestMatchers(HttpMethod.PUT, "/invoice/{id}").hasAnyRole("ADMIN", "OFFICE")
                .requestMatchers(HttpMethod.DELETE, "/invoice/{id}").hasAnyRole("ADMIN", "OFFICE")
                .requestMatchers(HttpMethod.PUT, "/invoice/{id}/generateinvoicepdf").hasAnyRole("ADMIN", "OFFICE")
                .requestMatchers(HttpMethod.GET, "/invoice/{id}/getpdfinvoice").hasAnyRole("ADMIN", "OFFICE")
//                ENDPOINTS INSPECTION
                .requestMatchers(HttpMethod.POST, "/inspection").hasAnyRole("ADMIN", "OFFICE", "MECHANIC")
                .requestMatchers(HttpMethod.GET, "/inspection").hasAnyRole("ADMIN", "OFFICE", "MECHANIC")
                .requestMatchers(HttpMethod.POST, "/inspection/add/{licenseplate}").hasAnyRole("ADMIN", "OFFICE", "MECHANIC")
                .requestMatchers(HttpMethod.GET, "/inspection/{id}").hasAnyRole("ADMIN", "OFFICE", "MECHANIC")
                .requestMatchers(HttpMethod.PUT, "/inspection/{id}").hasAnyRole("ADMIN", "OFFICE", "MECHANIC")
                .requestMatchers(HttpMethod.DELETE, "/inspection/{id}").hasAnyRole("ADMIN", "OFFICE", "MECHANIC")
                .requestMatchers(HttpMethod.PATCH, "/inspection/{id}/client-approval").hasRole("OFFICE")
//                ENDPOINTS REPAIR
                .requestMatchers(HttpMethod.POST, "/repair").hasAnyRole("ADMIN", "OFFICE", "MECHANIC")
                .requestMatchers(HttpMethod.GET, "/repair").hasAnyRole("ADMIN", "OFFICE", "MECHANIC")
                .requestMatchers(HttpMethod.POST, "/repair/add/{carpart}/{inspection_id}").hasAnyRole("ADMIN", "OFFICE", "MECHANIC")
                .requestMatchers(HttpMethod.POST, "/repair/carparts/{inspection_id}").hasAnyRole("ADMIN", "OFFICE", "MECHANIC")
                .requestMatchers(HttpMethod.GET, "/repair/lp/{licenseplate}").hasAnyRole("ADMIN", "OFFICE", "MECHANIC")
                .requestMatchers(HttpMethod.PATCH, "/repair/part_repaired/{id}").hasAnyRole("ADMIN", "MECHANIC")
                .requestMatchers(HttpMethod.GET, "/repair/{id}").hasAnyRole("ADMIN", "OFFICE", "MECHANIC")
                .requestMatchers(HttpMethod.PUT, "/repair/{id}").hasAnyRole("ADMIN", "OFFICE", "MECHANIC")
                .requestMatchers(HttpMethod.DELETE, "/repair/{id}").hasAnyRole("ADMIN", "OFFICE", "MECHANIC")
//                ENDPOINTS CARPARTS
                .requestMatchers(HttpMethod.GET, "/parts").hasAnyRole("ADMIN", "OFFICE", "MECHANIC")
                .requestMatchers(HttpMethod.GET, "/parts/license/{licenseplate}").hasAnyRole("ADMIN", "OFFICE", "MECHANIC")
                .requestMatchers(HttpMethod.GET, "/parts/{id}").hasAnyRole("ADMIN", "OFFICE", "MECHANIC")
                .requestMatchers(HttpMethod.DELETE, "/parts/{id}").hasAnyRole("ADMIN", "OFFICE", "MECHANIC")
                .requestMatchers(HttpMethod.PUT, "/parts/{licenseplate}/status/{carpart}").hasAnyRole("ADMIN", "OFFICE", "MECHANIC")
//                ENDPOINTS AUTHENTICATION
                .requestMatchers("/authenticated").authenticated()
                .requestMatchers(HttpMethod.POST,"/authenticate").permitAll()

                // Je mag meerdere paths tegelijk definieren
//                .requestMatchers("/invoices", "/repair", "/televisions", "/wallbrackets").hasAnyRole("ADMIN", "USER")

                .anyRequest().denyAll()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
