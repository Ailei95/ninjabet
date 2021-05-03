package io.ninjabet.auth.service;

import io.ninjabet.auth.entity.User;
import io.ninjabet.auth.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public Optional<User> getUserByEmail(String email) {
        return this.userRepository.findById(email);
    }

    public Optional<User> addUser(User user) {
        Date actionDate = new Date();
        Optional<User> localUser = getUserByEmail(user.getEmail());

        if (localUser.isPresent()) {
            return Optional.empty();
        }

        user.setAdmin(false);
        user.setVerify(false);
        user.setRegistrationDate(actionDate);
        user.setLastPasswordChangeDate(actionDate);

        return Optional.of(this.userRepository.save(user));
    }
}
