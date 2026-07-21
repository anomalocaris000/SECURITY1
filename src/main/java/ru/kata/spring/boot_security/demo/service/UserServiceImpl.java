package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("User with id " + id + " not found"));
    }



    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }


    @Override
    public void save(User user) {

        user.setPassword(
                passwordEncoder.encode(user.getPassword())
        );

        userRepository.save(user);
    }





    @Override
    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User with username '" + username + "' not found"));
    }


    @Override
    public void update(User user) {


        User oldUser = userRepository.findById(user.getId())
                .orElseThrow();



        oldUser.setUsername(
                user.getUsername()
        );


        oldUser.setEmail(
                user.getEmail()
        );


        oldUser.setRoles(
                user.getRoles()
        );



        if (user.getPassword() != null
                && !user.getPassword().isEmpty()) {


            oldUser.setPassword(
                    passwordEncoder.encode(
                            user.getPassword()
                    )
            );

        }



        userRepository.save(oldUser);

    }
}