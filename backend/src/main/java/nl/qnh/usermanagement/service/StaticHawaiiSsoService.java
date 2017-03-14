package nl.qnh.usermanagement.service;

import nl.qnh.usermanagement.model.User;
import nl.qnh.web.exception.UnauthorizedRequestException;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.requireNonNull;

/**
 * {@inheritDoc}
 */
@Component
public class StaticHawaiiSsoService implements HawaiiSsoService {

    /**
     * The users.
     */
    private static final Map<Long, User> USERS = new ConcurrentHashMap<>();

    /**
     * The users' passwords.
     */
    private static final Map<Long, String> PASSWORDS = new ConcurrentHashMap<>();

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("PMD.LawOfDemeter")
    @Override
    public UUID getSubjectIdFor(@NotNull final User user) {
        requireNonNull(user);
        requireNonNull(user.getEmailAddress());

        final User user2 = USERS.get(user.getId());
        if (user2 == null) {
            return null;
        }
        return user2.getSubjectId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createUser(@NotNull final User user, @NotNull final String password) {
        requireNonNull(user);
        requireNonNull(user.getEmailAddress());
        requireNonNull(password);

        user.setSubjectId(UUID.randomUUID());
        USERS.put(user.getId(), user);
        PASSWORDS.put(user.getId(), password);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("PMD.LawOfDemeter")
    @Override
    public void changePassword(@NotNull final User user, @NotNull final String currentPassword, @NotNull final String newPassword) {
        requireNonNull(user);
        requireNonNull(user.getEmailAddress());
        requireNonNull(currentPassword);
        requireNonNull(newPassword);

        final Long id = user.getId();
        if (PASSWORDS.get(id).equals(currentPassword)) {
            PASSWORDS.put(id, newPassword);
        } else {
            throw new UnauthorizedRequestException("Passwords mismatch.");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resetPassword(@NotNull final User user, @NotNull final String newPassword) {
        // not yet implemented
    }
}
