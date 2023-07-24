package nl.autogarage.finalassignmentbackendmain.service;

import nl.autogarage.finalassignmentbackendmain.dto.UserDto;
import nl.autogarage.finalassignmentbackendmain.exceptions.BadRequestException;
import nl.autogarage.finalassignmentbackendmain.exceptions.InvalidPasswordException;
import nl.autogarage.finalassignmentbackendmain.models.User;
import nl.autogarage.finalassignmentbackendmain.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Lazy;

@Service
public class UserService {


//    @Autowired
//    @Lazy
//    private PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    public String createUser (UserDto userDto) {
//        if (userDto == null) {
//            throw new BadRequestException("User cannot be empty");
//        }
//        if (userDto.getPassword() == null) {
//            ("Password can't be empty");
//        } else {
//            throw new InvalidPasswordException("Your password must contain: \n 6 characters");
//        }
//
//    }

    public User TransferDtoToUser(UserDto userDto) {
        User user = new User();

        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setFirstname(userDto.getFirstname());
        user.setLastname(userDto.getLastname());
        user.setEnabled(userDto.getEnabled());
        return user;
    }


//    public static UserDto transferUserToDto(User user) {
//
//        UserDto userDto = new UserDto();
//
//        userDto.setUsername(user.getUsername());
//        userDto.setPassword(user.getPassword());
//        userDto.setEnabled(user.isEnabled());
//        userDto.setApikey(user.getApikey());
//        userDto.setEmail(user.getEmail());
////        userDto.setAuthorities(user.getAuthorities());
//        userDto.setFirstname(user.getFirstname());
//        userDto.setLastname(user.getLastname());
//        userDto.setCars(user.getCars());
//        userDto.setInvoices(user.getInvoices());
//        return userDto;
//    }


}
