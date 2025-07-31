package com.dx.expense.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.dx.expense.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServicesTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServices userService;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    void mustRegisterUser() {

        // Working...
    }

    @Test
    void mustLoginUser() {

    }
}
