package com.uj.expenseManager.service;

import com.uj.expenseManager.dto.UserLoginDTO;
import com.uj.expenseManager.exception.ResponseStatusException;
import com.uj.expenseManager.models.User;
import com.uj.expenseManager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Retrieve all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Retrieve users by partial or full match in fullName (case-insensitive)
    public List<User> getUserByName(String namePart) {
        return userRepository.findByName(namePart);
    }

    // Update an existing user. Returns updated user or empty Optional if not found.
    public User updateUser(User updated) {
        getUserByUsername(updated.getUsername());
        return userRepository.save(updated);
    }

    // Delete user by id. Returns true if deleted, false if not found.
    public void deleteUser(String username) {
        User user = getUserByUsername(username);
        userRepository.delete(user);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with username " + username + " not found"));
    }

    public User register(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists!");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User authenticate(UserLoginDTO userLoginDTO) {
        User user = userRepository.findByUsername(userLoginDTO.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid username!"));
        if (!passwordEncoder.matches(userLoginDTO.getPassword(), user.getPassword())) {
            System.out.println(user.getPassword());
            System.out.println(userLoginDTO.getPassword());
            throw new RuntimeException("Invalid password!");
        }
        return user;
    }

}
