package nl.autogarage.finalassignmentbackendmain.service;

import nl.autogarage.finalassignmentbackendmain.dto.Security.UserDto;
import nl.autogarage.finalassignmentbackendmain.exceptions.RecordNotFoundException;
import nl.autogarage.finalassignmentbackendmain.exceptions.UsernameNotFoundException;
import nl.autogarage.finalassignmentbackendmain.models.Authority;
import nl.autogarage.finalassignmentbackendmain.models.User;
import nl.autogarage.finalassignmentbackendmain.repositories.UserRepository;
import nl.autogarage.finalassignmentbackendmain.utils.RandomStringGenerator;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    public List<UserDto> getUsers() {
        List<UserDto> collection = new ArrayList<>();
        List<User> list = userRepository.findAll();
        for (User user : list) {
            collection.add(fromUserToDto(user));
        }
        return collection;
    }

    public UserDto getUser(String username) {
        UserDto userDto;
        Optional<User> user = userRepository.findById(username);
        if (user.isPresent()) {
            userDto = fromUserToDto(user.get());
        } else {
            throw new UsernameNotFoundException(username);
        }
        return userDto;
    }

    public boolean userExists(String username) {
        return userRepository.existsById(username);
    }

    public String createUser(UserDto userDto) {
        String randomString = RandomStringGenerator.generateAlphaNumeric(20);
        userDto.setApikey(randomString);
//            hier decode ik hem al
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User newUser = userRepository.save(fromDtoToUser(userDto));
        return newUser.getUsername();
    }

    public void deleteUser(String username) {
        userRepository.deleteById(username);
    }

    public void updateUser(String username, UserDto newUser) {
        if (!userRepository.existsById(username)) {
            throw new RecordNotFoundException("User with username " + username + " not found.");
        }
        User user = userRepository.findById(username).get();
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        user.setEmail(newUser.getEmail());
        user.setFirstname(newUser.getFirstname());
        user.setLastname(newUser.getLastname());
        userRepository.save(user);
    }
//    Todo eisen voor wachtwoord e.d.

//    Voor admin
    public Set<Authority> getAuthorities(String username) {
        if (!userRepository.existsById(username)) throw new UsernameNotFoundException(username);
        User user = userRepository.findById(username).get();
        UserDto userDto = fromUserToDto(user);
        return userDto.getAuthorities();
    }

//    Voor admin

    public void addAuthority(String username, String authority) {
        if (!userRepository.existsById(username)) throw new UsernameNotFoundException(username);
        User user = userRepository.findById(username).get();
        user.addAuthority(new Authority(username, authority));
        userRepository.save(user);
    }

//    Voor admin

    public void removeAuthority(String username, String authority) {
        if (!Objects.equals(authority, "ROLE_OFFICE") & !Objects.equals(authority, "ROLE_ADMIN") & !Objects.equals(authority, "ROLE_MECHANIC")) {
            throw new RecordNotFoundException(" please use one of these roles: ROLE_OFFICE, ROLE_ADMIN, ROLE_MECHANIC");
        }
        if (!userRepository.existsById(username))
            throw new org.springframework.security.core.userdetails.UsernameNotFoundException(username);
        try {
            User user = userRepository.findById(username).get();
            Authority authorityToRemove = user.getAuthorities().stream().filter((a) -> a.getAuthority().equalsIgnoreCase(authority)).findAny().get();
            System.out.println(authorityToRemove);
            user.removeAuthority(authorityToRemove);
            userRepository.save(user);
        } catch (Exception e) {
            throw new RecordNotFoundException("that role has been removed");
        }
    }

    public static UserDto fromUserToDto(User user) {

        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        userDto.setEnabled(user.isEnabled());
        userDto.setApikey(user.getApikey());
        userDto.setEmail(user.getEmail());
        userDto.setAuthorities(user.getAuthorities());
        userDto.setFirstname(user.getFirstname());
        userDto.setLastname(user.getLastname());
        userDto.setCars(user.getCars());

        return userDto;
    }

    public User fromDtoToUser(UserDto userDto) {

        User user = new User();

        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setEnabled(userDto.getEnabled());
        user.setApikey(userDto.getApikey());
        user.setEmail(userDto.getEmail());
        user.setCars(userDto.getCars());
        user.setFirstname(userDto.getFirstname());
        user.setLastname(userDto.getLastname());


        return user;
    }

}
