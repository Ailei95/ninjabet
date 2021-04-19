package io.ninjabet.auth.service;

import io.ninjabet.auth.entity.User;
import io.ninjabet.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) { this.userRepository = userRepository; }

    public Optional<User> getUserByEmail(String email) {
        return this.userRepository.findById(email);
    }

    public User addUser(User user) {
        return this.userRepository.save(user);
    }
}
