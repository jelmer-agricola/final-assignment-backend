package nl.autogarage.finalassignmentbackendmain.config;

import nl.autogarage.finalassignmentbackendmain.filter.JwtRequestFilter;
import nl.autogarage.finalassignmentbackendmain.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
//Todo toevoegen endpoint
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig {

    public final CustomUserDetailsService customUserDetailsService;
    private final JwtRequestFilter jwtRequestFilter;

    public SpringSecurityConfig(CustomUserDetailsService customUserDetailsService, JwtRequestFilter jwtRequestFilter) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }

    @Bean
    protected SecurityFilterChain filter(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .httpBasic().disable()
                .cors().and()
                .authorizeHttpRequests()
//                 Wanneer je deze regel hieronder uncomment, staat de hele security open. Je hebt dan alleen nog een jwt nodig.
//                .requestMatchers("/**").permitAll()
//              ENDPOINTS USERS
                .requestMatchers(HttpMethod.POST, "/users/add/mechanic").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/users/add/office").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/users/{username}").hasAnyRole("ADMIN", "OFFICE")
                .requestMatchers(HttpMethod.GET, "/users").hasAnyRole("ADMIN", "OFFICE")
                .requestMatchers(HttpMethod.PUT, "/users/{username}").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/users/{username}").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/users/{username}/authorities").hasAnyRole("ADMIN", "OFFICE")
                .requestMatchers(HttpMethod.POST, "/users/{username}/authorities").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/users/{username}/authorities/{authority}").hasAnyRole("ADMIN")

//                ENDPOINTS CAR
                .requestMatchers(HttpMethod.POST, "/car/add").hasRole("MECHANIC")
                .requestMatchers(HttpMethod.GET, "/car").hasAnyRole("OFFICE", "MECHANIC")
                .requestMatchers(HttpMethod.GET, "/car/licenseplate").hasAnyRole("OFFICE", "MECHANIC")
                .requestMatchers(HttpMethod.PUT, "/car/licenseplate").hasAnyRole("OFFICE", "MECHANIC")
                .requestMatchers(HttpMethod.DELETE, "/car/delete").hasAnyRole("ADMIN", "OFFICE", "MECHANIC")

//                ENDPOINTS INVOICE
                .requestMatchers(HttpMethod.POST, "/invoice/add/{inspection_id}").hasAnyRole("ADMIN", "OFFICE")
                .requestMatchers(HttpMethod.GET, "/invoice").hasAnyRole("ADMIN", "OFFICE")
                .requestMatchers(HttpMethod.GET, "/invoice/{id}").hasAnyRole("ADMIN", "OFFICE")
                .requestMatchers(HttpMethod.GET, "/invoice/{licenseplate}/all").hasAnyRole("ADMIN", "OFFICE")
                .requestMatchers(HttpMethod.PUT, "/invoice/{id}/uploadinvoicepdf").hasAnyRole("ADMIN", "OFFICE")
                .requestMatchers(HttpMethod.GET, "/invoice/{id}/download-pdfinvoice").hasAnyRole("ADMIN", "OFFICE")
                .requestMatchers(HttpMethod.PATCH, "/invoice/{id}").hasAnyRole("ADMIN", "OFFICE")
                .requestMatchers(HttpMethod.DELETE, "/invoice/{id}").hasAnyRole("ADMIN", "OFFICE")

//                ENDPOINTS INSPECTION
                .requestMatchers(HttpMethod.POST, "/inspection/add/{licenseplate}").hasRole("MECHANIC")
                .requestMatchers(HttpMethod.GET, "/inspection").hasAnyRole("OFFICE", "MECHANIC")
                .requestMatchers(HttpMethod.GET, "/inspection/{id}").hasAnyRole("OFFICE", "MECHANIC")
                .requestMatchers(HttpMethod.PUT, "/inspection/{id}").hasAnyRole("ADMIN", "MECHANIC")
                .requestMatchers(HttpMethod.PATCH, "/inspection/{id}/client-approval").hasRole("OFFICE")
                .requestMatchers(HttpMethod.DELETE, "/inspection/{id}").hasAnyRole("OFFICE", "MECHANIC")


//                ENDPOINTS REPAIR
                .requestMatchers(HttpMethod.POST, "/repair/add/{carpart}/{inspection_id}").hasRole("MECHANIC")
                .requestMatchers(HttpMethod.POST, "/repair/carparts/{inspection_id}").hasRole("MECHANIC")
                .requestMatchers(HttpMethod.GET, "/repair").hasAnyRole("ADMIN", "OFFICE", "MECHANIC")
                .requestMatchers(HttpMethod.GET, "/repair/{id}").hasAnyRole("ADMIN", "OFFICE", "MECHANIC")
                .requestMatchers(HttpMethod.GET, "/repair/lp/{licenseplate}").hasAnyRole("ADMIN", "OFFICE", "MECHANIC")
                .requestMatchers(HttpMethod.PUT, "/repair/part_repaired/{id}").hasRole("MECHANIC")
                .requestMatchers(HttpMethod.DELETE, "/repair/{id}").hasAnyRole("OFFICE", "MECHANIC")

//                ENDPOINTS CARPARTS
                .requestMatchers(HttpMethod.GET, "/parts/license/{licenseplate}").hasAnyRole( "OFFICE", "MECHANIC")
                .requestMatchers(HttpMethod.GET, "/parts").hasAnyRole("ADMIN", "OFFICE", "MECHANIC")
                .requestMatchers(HttpMethod.GET, "/parts/{id}").hasAnyRole("ADMIN", "OFFICE", "MECHANIC")
                .requestMatchers(HttpMethod.PUT, "/parts/{licenseplate}/status/{carpart}").hasAnyRole( "MECHANIC")

//                ENDPOINTS AUTHENTICATION
                .requestMatchers(HttpMethod.GET, "/authenticated").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/authenticate").permitAll()

                .anyRequest().denyAll()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
