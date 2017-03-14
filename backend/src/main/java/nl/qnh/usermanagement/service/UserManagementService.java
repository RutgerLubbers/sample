package nl.qnh.usermanagement.service;

import nl.qnh.usermanagement.model.User;
import nl.qnh.usermanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

/**
 * The service for user management tasks.
 */
@Component
public class UserManagementService {

    /**
     * The user repository.
     */
    private final UserRepository userRepository;

    /**
     * The SSO service.
     */
    private final HawaiiSsoService ssoService;

    /**
     * The constructor.
     */
    @Autowired
    public UserManagementService(final UserRepository userRepository
            , final HawaiiSsoService ssoService) {
        this.userRepository = requireNonNull(userRepository);
        this.ssoService = requireNonNull(ssoService);
    }

    /**
     * Find all users.
     *
     * @return a possibly empty list of users.
     */
    @SuppressWarnings("PMD.LawOfDemeter")
    public List<User> findAll() {
        return Optional.of(userRepository.findAll()).orElse(new ArrayList<>());
    }

    /**
     * Retrieve a user by it's id.
     *
     * @param id the user's id.
     * @return The user
     */
    @SuppressWarnings("PMD.ShortVariable")
    public Optional<User> getUser(final Long id) {
        return Optional.ofNullable(userRepository.findById(id));
    }

    /**
     * Delete the user. This does not delete the user from the SSO.
     *
     * @param user the user to delete.
     */
    public void deleteUser(final User user) {
        userRepository.deleteUser(user);
    }

    /**
     * Update the user. This does not update the user in the SSO.
     *
     * @param user the user to updateUser.
     */
    public void updateUser(final User user) {
        userRepository.updateUser(user);
    }

    /**
     * Create a new user
     *
     * @param user the user to create
     */
    public void createUser(final User user) {
        userRepository.createUser(user);
    }

    /**
     * Try and find the user in the SSO.
     *
     * @param user the user to check.
     * @return true if the user exists in the sso.
     */
    public boolean userExistsInSso(final User user) {
        return ssoService.getSubjectIdFor(user) != null;
    }

    /**
     * This links the user to the `sso user`. The user's 'subject id' is filled with the value from the SSO.
     * <p>
     * Precondition is that the user is already known in the SSO.
     *
     * @param user the user to link to the SSO.
     */
    public void linkUserToSsoUser(final User user) {
        final UUID subjectId = requireNonNull(ssoService.getSubjectIdFor(user));

        user.setSubjectId(subjectId);
        updateUser(user);
    }

    /**
     * Creates a new SSO user.
     *
     * @param user     the user to create in the SSO.
     * @param password the user's password.
     */
    public void createSsoUser(final User user, final String password) {
        ssoService.createUser(user, password);
    }

    /**
     * Changes the user's password in the SSO.
     *
     * @param user            the user.
     * @param currentPassword the current password.
     * @param newPassword     the new password.
     */
    public void changePassword(final User user, final String currentPassword, final String newPassword) {
        ssoService.changePassword(user, currentPassword, newPassword);
    }

    /**
     * Find a user by it's subject id. SubjectID is the SSO's unique id for a user.
     *
     * @param subjectId the user's subject id.
     */
    public User findUserBySubjectId(final String subjectId) {
        return findUserBySubjectId(UUID.fromString(subjectId));
    }

    /**
     * Find a user by it's subject id. SubjectID is the SSO's unique id for a user.
     *
     * @param subjectId the user's subject id.
     */
    public User findUserBySubjectId(final UUID subjectId) {
        return userRepository.findUserBySubjectId(subjectId);
    }
}
