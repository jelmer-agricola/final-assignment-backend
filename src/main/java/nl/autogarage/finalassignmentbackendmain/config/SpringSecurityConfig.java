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
    protected SecurityFilterChain filter (HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .httpBasic().disable()
                .cors().and()
                .authorizeHttpRequests()
                // Wanneer je deze uncomments, staat je hele security open. Je hebt dan alleen nog een jwt nodig.
//                .requestMatchers("/**").permitAll()
//                .requestMatchers(HttpMethod.POST, "/users").permitAll()
//                .requestMatchers(HttpMethod.GET,"/users").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.POST,"/users/**").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.DELETE, "/users/**").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.POST, "/cimodules").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.DELETE, "/cimodules/**").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.POST, "/remotecontrollers").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.DELETE, "/remotecontrollers/**").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.POST, "/televisions").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.DELETE, "/televisions/**").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.POST, "/wallbrackets").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.DELETE, "/wallbrackets/**").hasRole("ADMIN")
//                // Je mag meerdere paths tegelijk definieren
                .requestMatchers("/cimodules", "/remotecontrollers", "/televisions", "/wallbrackets").hasAnyRole("ADMIN", "USER")
                .requestMatchers("/authenticated").authenticated()
                .requestMatchers("/authenticate").permitAll()
                .anyRequest().denyAll()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
