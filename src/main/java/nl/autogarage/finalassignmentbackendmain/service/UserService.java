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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;
//Password encoder in een apparte klasse dan kan je hem zonder autowired en lazy importeren

    public UserService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    public List<UserDto> getUsers() {
        List<UserDto> collection = new ArrayList<>();
        List<User> list = userRepository.findAll();
        for (User user : list) {
            collection.add(fromUser(user));
        }
        return collection;
    }

    public UserDto getUser(String username) {
        UserDto userDto;
        Optional<User> user = userRepository.findById(username);
        if (user.isPresent()) {
            userDto = fromUser(user.get());
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
            User newUser = userRepository.save(toUser(userDto));
            return newUser.getUsername();
        }

        public void deleteUser(String username) {
            userRepository.deleteById(username);
        }

        public void updateUser(String username, UserDto newUser) {
            if (!userRepository.existsById(username)) throw new RecordNotFoundException();
            User user = userRepository.findById(username).get();
            user.setPassword(newUser.getPassword());
            userRepository.save(user);
        }

        public Set<Authority> getAuthorities(String username) {
            if (!userRepository.existsById(username)) throw new UsernameNotFoundException(username);
            User user = userRepository.findById(username).get();
            UserDto userDto = fromUser(user);
            return userDto.getAuthorities();
        }

        public void addAuthority(String username, String authority) {

            if (!userRepository.existsById(username)) throw new UsernameNotFoundException(username);
            User user = userRepository.findById(username).get();
            user.addAuthority(new Authority(username, authority));
            userRepository.save(user);
        }

        public void removeAuthority(String username, String authority) {
            if (!userRepository.existsById(username)) throw new UsernameNotFoundException(username);
            User user = userRepository.findById(username).get();
            Authority authorityToRemove = user.getAuthorities().stream().filter((a) -> a.getAuthority().equalsIgnoreCase(authority)).findAny().get();
            user.removeAuthority(authorityToRemove);
            userRepository.save(user);
        }

    public static UserDto fromUser(User user) {


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
        userDto.setInvoices(user.getInvoices());
        return userDto;
    }

    public User toUser(UserDto userDto) {

        var user = new User();

        user.setUsername(userDto.getUsername());
        if (userDto.password != null) {
            user.setPassword(passwordEncoder.encode(userDto.password));
        }        user.setEnabled(userDto.getEnabled());


        user.setApikey(userDto.getApikey());
        user.setEmail(userDto.getEmail());
        user.setInvoices(userDto.getInvoices());
        user.setCars(userDto.getCars());
        user.setFirstname(userDto.getFirstname());
        user.setLastname(userDto.getLastname());


        return user;
    }

}
