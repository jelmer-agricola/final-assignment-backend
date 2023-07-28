package nl.autogarage.finalassignmentbackendmain.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.*;

import nl.autogarage.finalassignmentbackendmain.dto.Security.UserDto;
import nl.autogarage.finalassignmentbackendmain.exceptions.RecordNotFoundException;

import nl.autogarage.finalassignmentbackendmain.exceptions.UsernameNotFoundException;
import nl.autogarage.finalassignmentbackendmain.models.Authority;

import nl.autogarage.finalassignmentbackendmain.models.User;
import nl.autogarage.finalassignmentbackendmain.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    private UserRepository userRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    User user1;
    User user2;
    User user3;
    UserDto userDto1;
    UserDto userDto2;
    UserDto userDto3;


    @BeforeEach
    void setUp() {
        user1 = new User("user1", "user", true, "testapikey", "testuser@email.com", "Bart", "Test", null);
        user2 = new User("user2", "user2", true, "testapikey", "testuser2@email.com", "Bart2", "Test2", null);


        userDto1 = new UserDto("user1", "user", "testuser@email.com", true, "Bart", "Test", "testapikey", null, null, null);
        userDto2 = new UserDto("user2", "user2", "testuse2r@email.com", true, "Bart2", "Test2", "testapikey", null, null, null);


    }


    @Test
    void testGetUsers() {
        // Arrange
        List<User> actualList = new ArrayList<>();
        actualList.add(user1);
        actualList.add(user2);

        List<UserDto> expectList = new ArrayList<>();
        expectList.add(userDto1);
        expectList.add(userDto2);
        when(userRepository.findAll()).thenReturn(actualList);
//      Act
        List<UserDto> findUsers = userService.getUsers();
//      Assert
        assertEquals(expectList.get(0).getFirstname(), findUsers.get(0).getFirstname());
        assertEquals(expectList.get(1).getFirstname(), findUsers.get(1).getFirstname());
        assertEquals(expectList.get(0).getLastname(), findUsers.get(0).getLastname());
        assertEquals(expectList.get(0).getPassword(), findUsers.get(0).getPassword());
    }

    @Test
    void testGetUser() {
        // Arrange
        String username = "user1";
        when(userRepository.findById(username)).thenReturn(Optional.of(user1));
        // Act
        UserDto result = userService.getUser(username);
        // Assert
        assertNotNull(result);
        assertEquals(userDto1.getFirstname(), result.getFirstname());
        assertEquals(userDto1.getLastname(), result.getLastname());
        assertEquals(userDto1.getEmail(), result.getEmail());
        assertEquals(userDto1.getUsername(), result.getUsername());
    }

    @Test
    void testGetUserShouldThrowUsernameNotFoundException() {
        // Arrange
        String username = "nonexistentuser";
        when(userRepository.findById(username)).thenReturn(Optional.empty());
        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> userService.getUser(username));
    }
}