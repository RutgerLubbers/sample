package nl.qnh.usermanagement.repository;

import nl.qnh.usermanagement.model.User;

import java.util.List;
import java.util.UUID;

/**
 * The repository for user management. This repository interfaces with the SSO data structures.
 */
public interface UserRepository {

    /**
     * Return a user by it's id.
     * <p>
     * Will return null if no user is found.
     */
    @SuppressWarnings("PMD.ShortVariable")
    User findById(final Long userId);

    /**
     * Create a new user.
     */
    User createUser(final User user);

    /**
     * Delete user.
     */
    User deleteUser(final User user);

    /**
     * Update user.
     */
    User updateUser(final User user);

    /**
     * Find all users.
     *
     * @return a possibly null list of users.
     */
    List<User> findAll();

    /**
     * Find a user by it's subject id.
     *
     * @param subjectId The subject id to search for.
     * @return The user, or null if no user is found.
     */
    User findUserBySubjectId(UUID subjectId);
}
