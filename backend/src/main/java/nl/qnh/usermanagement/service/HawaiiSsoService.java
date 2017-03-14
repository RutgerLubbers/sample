package nl.qnh.usermanagement.service;

import nl.qnh.usermanagement.model.User;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * The interface to the Hawaii SSO.
 */
public interface HawaiiSsoService {

    /**
     * Returns the SSO's unique identifier (subject_id) for the user. This will always perform a lookup to the SSO, even if
     * the user already has a subject id.
     *
     * @param user the non null user to lookup.
     * @return the subject_id or null.
     */
    UUID getSubjectIdFor(@NotNull User user);

    /**
     * Creates a new user at the SSO.
     *
     * @param user     the non null user to create.
     * @param password the non null password to set.
     */
    void createUser(@NotNull User user, @NotNull String password);

    /**
     * Change the user's password.
     *
     * @param user            the user.
     * @param currentPassword the current password.
     * @param newPassword     the new password.
     */
    void changePassword(@NotNull User user, @NotNull String currentPassword, @NotNull String newPassword);

    /**
     * Reset the user's password.
     *
     * @param user the user.
     * @param newPassword the new password.
     */
    void resetPassword(@NotNull User user, @NotNull String newPassword);
}
