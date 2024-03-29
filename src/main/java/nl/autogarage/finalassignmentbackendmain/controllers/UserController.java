package nl.autogarage.finalassignmentbackendmain.controllers;
import nl.autogarage.finalassignmentbackendmain.dto.Security.UserDto;
import nl.autogarage.finalassignmentbackendmain.exceptions.BadRequestException;
import nl.autogarage.finalassignmentbackendmain.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/add/mechanic")
    public ResponseEntity<UserDto> createMechanic(@RequestBody UserDto userDto) {;

        String newUsername = userService.createUser(userDto);
        userService.addAuthority(newUsername, "ROLE_MECHANIC");
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{username}")
                .buildAndExpand(newUsername).toUri();

        return ResponseEntity.created(location).build();
    }

    @PostMapping(value = "/add/office")
    public ResponseEntity<UserDto> createOfficeEmployee(@RequestBody UserDto userDto) {
        String newUsername = userService.createUser(userDto);
        userService.addAuthority(newUsername, "ROLE_OFFICE");
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{username}")
                .buildAndExpand(newUsername).toUri();

        return ResponseEntity.created(location)
                .build();
    }


    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers() {
        List<UserDto> userDtos = userService.getUsers();
        return ResponseEntity.ok().body(userDtos);
    }

    @GetMapping(value = "/{username}")
    public ResponseEntity<UserDto> getUser(@PathVariable("username") String username) {

        UserDto optionalUser = userService.getUser(username);
        return ResponseEntity.ok().body(optionalUser);

    }

    @PutMapping(value = "/{username}")
    public ResponseEntity<String> updateUser(@PathVariable("username") String username, @RequestBody UserDto userDto) {
        userService.updateUser(username, userDto);
        return ResponseEntity.ok("User with username " + username + " updated successfully.");
    }


    @DeleteMapping(value = "/{username}")
    public ResponseEntity<Object> deleteUser(@PathVariable("username") String username) {
        userService.deleteUser(username);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{username}/authorities")
    public ResponseEntity<Object> getUserAuthorities(@PathVariable("username") String username) {
        return ResponseEntity.ok().body(userService.getAuthorities(username));
    }

    @PostMapping(value = "/{username}/authorities")
    public ResponseEntity<Object> addUserAuthority(@PathVariable("username") String username, @RequestBody Map<String, Object> fields) {
        try {
            String authorityName = (String) fields.get("authority");
            userService.addAuthority(username, authorityName);
            return ResponseEntity.noContent().build();
        }
        catch (Exception ex) {
            throw new BadRequestException();
        }
    }

    @DeleteMapping(value = "/{username}/authorities/{authority}")
    public ResponseEntity<Object> deleteUserAuthority(@PathVariable("username") String username, @PathVariable("authority") String authority) {
        userService.removeAuthority(username, authority);
        return ResponseEntity.noContent().build();
    }
}
