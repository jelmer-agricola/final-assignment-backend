package nl.autogarage.finalassignmentbackendmain.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserService.class})
@ExtendWith(SpringExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserServiceTest {
    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;


    @Captor
    private ArgumentCaptor<User> userCaptor;
    User user1;
    User user2;
    User user3;
    UserDto userDto1;
    UserDto userDto2;
    UserDto userDto3;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

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

    @Test
    void testUserExists() {
        when(userRepository.existsById((String) any())).thenReturn(true);
        assertTrue(userService.userExists("janedoe"));
        verify(userRepository).existsById((String) any());
    }


    @Test
    void testDeleteUser() {
        // Arrange
        String username = "user1";
        doNothing().when(userRepository).deleteById(username);
        when(userRepository.existsById(username)).thenReturn(true);

        // Act
        userService.deleteUser(username);

        // Assert
        verify(userRepository, times(1)).deleteById(username);
    }


    @Test
    void testCreateUser() {
        // Arrange
        User user = mock(User.class);
        when(user.getUsername()).thenReturn("janedoe");

        UserDto userDto = new UserDto();
        userDto.setEnabled(true);
        userDto.setPassword("testPassword");  // add this line

        when(userRepository.save(any(User.class))).thenReturn(user);
        when(passwordEncoder.encode(any(CharSequence.class))).thenReturn("secret");

        // Act
        String createdUsername = userService.createUser(userDto);

        // Assert
        assertEquals("janedoe", createdUsername, "Created username should be 'janedoe'");
        verify(userRepository, times(1)).save(any(User.class));
        verify(passwordEncoder, times(1)).encode(any(CharSequence.class));
        assertEquals("secret", userDto.getPassword(), "UserDto password should be equal to 'secret'");
    }

}

