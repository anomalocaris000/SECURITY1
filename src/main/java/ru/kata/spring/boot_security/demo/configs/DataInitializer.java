package ru.kata.spring.boot_security.demo.configs;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public DataInitializer(RoleRepository roleRepository,
                           UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void run(String... args) {

        Role adminRole = roleRepository.findByRole("ROLE_ADMIN");

        if (adminRole == null) {
            adminRole = new Role("ROLE_ADMIN");
            roleRepository.save(adminRole);
        }


        Role userRole = roleRepository.findByRole("ROLE_USER");

        if (userRole == null) {
            userRole = new Role("ROLE_USER");
            roleRepository.save(userRole);
        }


        if (userRepository.findByUsername("admin") == null) {

            Set<Role> adminRoles = new HashSet<>();
            adminRoles.add(adminRole);

            User admin = new User(
                    "admin",
                    passwordEncoder.encode("admin"),
                    "admin@mail.ru",
                    adminRoles
            );

            userRepository.save(admin);
        }


        if (userRepository.findByUsername("user") == null) {

            Set<Role> userRoles = new HashSet<>();
            userRoles.add(userRole);

            User user = new User(
                    "user",
                    passwordEncoder.encode("user"),
                    "user@mail.ru",
                    userRoles
            );

            userRepository.save(user);
        }
    }
}