package com.dx.expense.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dx.expense.dto.LoginDTO;
import com.dx.expense.dto.RegisterDTO;
import com.dx.expense.entities.User;
import com.dx.expense.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServicesTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServices userService;

    @Test
    void mustRegisterUser() {

        RegisterDTO register = new RegisterDTO("teste@teste", "1234567", "teste");

        userService.registeredUser(register);

    }

    @Test
    void mustLoginUser() {

        // Arrange
        LoginDTO login = new LoginDTO("teste@teste", "1234567");

        User user = new User("teste", "teste@teste", "1234567");

        Mockito.when(userRepository.findByLogin(login.email())).thenReturn(Optional.of(user));

        // Act
        User test = userService.loginResponse(login);

        // Asserty
        assertEquals(user.getLogin(), test.getLogin());
    }
}
