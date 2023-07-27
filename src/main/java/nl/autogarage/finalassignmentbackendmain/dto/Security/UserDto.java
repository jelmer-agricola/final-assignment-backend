package nl.autogarage.finalassignmentbackendmain.dto.Security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.autogarage.finalassignmentbackendmain.models.Authority;
import nl.autogarage.finalassignmentbackendmain.models.Car;
import nl.autogarage.finalassignmentbackendmain.models.Invoice;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    public String username;
    public String password;
    public String email;
    public Boolean enabled;
    private String firstname;
    private String lastname;
    public String apikey;

    @JsonSerialize
    public Set<Authority> authorities;

    @JsonIgnore
    public List<Car> cars;

    @JsonIgnore
    public List<Invoice> invoices;



    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }
}