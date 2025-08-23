package com.dx.expense.configs;

import com.dx.expense.entities.Role;
import com.dx.expense.entities.User;
import com.dx.expense.repository.RoleRepository;
import com.dx.expense.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;

@Configuration
public class AdminUserConfig implements CommandLineRunner {

    private RoleRepository roleRepository;

    private UserRepository userRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public AdminUserConfig(RoleRepository roleRepository, UserRepository userRepository,
            BCryptPasswordEncoder bCryptPasswordEncoder) {

        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;

    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        var roleAdmin = roleRepository.findByDescription(Role.Types.ADMIN.getDescription());

        var userAdmin = userRepository.findByName("admin");

        userAdmin.ifPresentOrElse((user) -> System.out.println("Admin já existe"), () -> {
            var user = new User("admin", "admin@admin", bCryptPasswordEncoder.encode("admin"), Set.of(roleAdmin.get()));
            userRepository.save(user);
        });
    }

}
