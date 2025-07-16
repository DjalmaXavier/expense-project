package com.dx.expense.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dx.expense.dto.LoginDTO;
import com.dx.expense.dto.RegisterDTO;
import com.dx.expense.entities.User;
import com.dx.expense.repository.UserRepository;

@Service
public class UserServices {

    private final UserRepository userRepository;

    @Autowired
    public UserServices(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registeredUser(RegisterDTO registerDTO) {
        if (!userRepository.findByLogin(registerDTO.login()).isEmpty()) {
            throw new RuntimeException("Usuario já existe");
        }

        User user = new User(registerDTO.name(), registerDTO.login(), registerDTO.password());

        userRepository.save(user);

    };

    public User loginResponse(LoginDTO loginDTO) {
        try {
            User user = userRepository.findByLogin(loginDTO.email()).get();

            if (loginDTO.email().equals(user.getLogin()) && loginDTO.password().equals(user.getPassword())) {
                return user;
            }
            return null;

        } catch (Exception e) {
            return null;
        }

    }

}
