package nl.autogarage.finalassignmentbackendmain.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.List;
//Todo als ik hiermee verder ga security toevoegen en user vanuit tech it easy

@Getter
@Setter
    @Entity
    @Table(name="users")
    public class User {
        @Id
        private String username;

        private String password;

        private String firstname;
        private String lastname;


        @ManyToMany(fetch = FetchType.EAGER)
        private Collection<Role> roles;

    @OneToMany(mappedBy = "user")
    private List<Car> cars;

    @OneToMany(mappedBy = "user")
    private List<Invoice> invoices;
    }
