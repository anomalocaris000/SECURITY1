package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public UserServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }


    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElse(null);
    }


    @Override
    public void save(User user) {

        if (!user.getPassword().startsWith("$2a$")) {
            user.setPassword(
                    passwordEncoder.encode(user.getPassword())
            );
        }

        userRepository.save(user);
    }


    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }


    @Override
    public User findByUsername(String username) {

        return userRepository.findByUsername(username)
                .orElseThrow();
    }


    @Override
    public void update(User user) {

        User oldUser = userRepository.findById(user.getId())
                .orElseThrow();


        oldUser.setUsername(user.getUsername());
        oldUser.setEmail(user.getEmail());
        oldUser.setRoles(user.getRoles());


        if (!user.getPassword().isEmpty()) {
            oldUser.setPassword(
                    passwordEncoder.encode(user.getPassword())
            );
        }


        userRepository.save(oldUser);
    }
}