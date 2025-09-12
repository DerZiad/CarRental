package com.coding.app.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.coding.app.exceptions.InvalidObjectException;
import com.coding.app.exceptions.NotFoundException;
import com.coding.app.models.User;
import com.coding.app.models.enums.ServerRole;
import com.coding.app.repository.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * Service for managing user entities, including admin and manager creation, banning, and retrieval.
 */
@Service
@RequiredArgsConstructor
public class UserService {

    @Value("${server.admin.username}")
    private String defaultAdminUsername;

    @Value("${server.admin.password}")
    private String defaultManagerAdminPassword;

    @Value("${server.admin.email}")
    private String defaultAdminEmail;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final HistoryService historyService;

    /**
     * Creates the default administrator user.
     *
     * @throws InvalidObjectException if validation fails.
     */
    public void createAdmin() throws InvalidObjectException {
        final User user = new User();
        user.setValidated(true);
        user.setUsername(defaultAdminUsername);
        user.setEmail(defaultAdminEmail);
        user.setPassword(defaultManagerAdminPassword);
        final HashMap<String, String> errors = Utils.validate(user);
        if (errors.isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setEnabled(true);
            user.setValidated(true);
            user.addRole(ServerRole.ADMIN);
            userRepository.save(user);
            historyService.addHistory("New administrator added " + user.getUsername());
        }else {
            throw new InvalidObjectException("Invalid user object", errors);
        }
    }

    /**
     * Creates a manager user.
     *
     * @param user The user to create as manager.
     * @return The created User object.
     * @throws InvalidObjectException if validation fails.
     */
    public User createManager(final User user) throws InvalidObjectException {
        user.setPassword(defaultManagerAdminPassword);
        final HashMap<String, String> errors = Utils.validate(user);
        if (errors.isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setEnabled(true);
            user.setValidated(true);
            user.addRole(ServerRole.MANAGER);
            userRepository.save(user);
            historyService.addHistory("New manager added " + user.getUsername());
            return user;
        }else {
            throw new InvalidObjectException("Invalid user object", errors);
        }
    }

    /**
     * Checks if an admin user exists.
     *
     * @return true if admin exists, false otherwise.
     */
    public boolean adminExists() {
        return userRepository.findAll().stream().anyMatch(user -> user.getRoles().contains(ServerRole.ADMIN.name()));
    }

    /**
     * Bans (disables) a user by username.
     *
     * @param username The username to ban.
     * @throws NotFoundException if user is not found.
     */
    public void banUser(final String username) throws NotFoundException {
        User user = userRepository.findById(username).orElseThrow(()-> new NotFoundException("User not found"));
        user.setEnabled(false);
        userRepository.save(user);
    }

    /**
     * Finds users matching a filter predicate.
     *
     * @param filter The predicate to filter users.
     * @return List of matching User objects.
     */
    public List<User> findUsersByFilter(final Predicate<User> filter) {
        return userRepository.findAll().stream()
                .filter(filter)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Finds a user by username.
     *
     * @param username The username to search for.
     * @return The User object.
     * @throws NotFoundException if user is not found.
     */
    public User findByUsername(final String username) throws NotFoundException {
        return userRepository.findById(username).orElseThrow(()-> new NotFoundException("User not found"));
    }
}
