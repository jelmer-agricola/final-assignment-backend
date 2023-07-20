package nl.autogarage.finalassignmentbackendmain.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.List;
//Todo als ik hiermee verder ga security toevoegen en user vanuit tech it easy

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

//    Zoals in hw klas
    @Id
    @Column(nullable = false, unique = true)
    private String username;
    @NotNull(message = "You should have a password")
    private String password;
    @Column
    private String email;
    private String firstname;
    private String lastname;
    public String apikey;


    @Column(nullable = false)
    private boolean enabled = true;

    @OneToOne
    private Role role;


    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles;

    @OneToMany(mappedBy = "user")
    private List<Car> cars;

    @OneToMany(mappedBy = "user")
    private List<Invoice> invoices;
}
