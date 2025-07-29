package com.dx.expense.services;

import java.util.NoSuchElementException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.dx.expense.dto.LoginRequestDTO;
import com.dx.expense.dto.RegisterDTO;
import com.dx.expense.entities.Role;
import com.dx.expense.entities.User;
import com.dx.expense.repository.RoleRepository;
import com.dx.expense.repository.UserRepository;

@Service
public class UserServices {

    private final UserRepository userRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private RoleRepository roleRepository;

    @Autowired
    public UserServices(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
            RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleRepository = roleRepository;
    }

    public User registerUser(RegisterDTO registerDTO) {

        if (!userRepository.findByLogin(registerDTO.login()).isEmpty()) {
            throw new IllegalArgumentException("Usuario já existe!");
        }

        var basicRole = roleRepository.findByDescription(Role.Types.BASIC.getDescription());

        String password = bCryptPasswordEncoder.encode(registerDTO.password());

        return userRepository
                .save(new User(registerDTO.name(), registerDTO.login(), password, Set.of(basicRole.get())));
    };

    public User loginUser(LoginRequestDTO loginDTO) {

        User user = userRepository.findByLogin(loginDTO.email())
                .orElseThrow(() -> new NoSuchElementException("Usuário não existe!"));

        if (!user.isLoginCorrect(loginDTO, bCryptPasswordEncoder)) {
            throw new BadCredentialsException("Credenciais inválidas!");
        }

        return user;
    }
}
