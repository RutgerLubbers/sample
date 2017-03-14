package nl.qnh.usermanagement.repository;

import nl.qnh.usermanagement.model.UserInvitation;

import java.util.UUID;

/**
 * Repository for user invitations.
 */
public interface UserInvitationRepository {

    /**
     * Get the user invitation by it's id.
     *
     * @param id the user invitation's id
     * @return the user invitation, or null.
     */
    @SuppressWarnings("PMD.ShortVariable")
    UserInvitation findById(UUID id);

    /**
     * Delete the user invitation.
     *
     * @param userInvitation the user invitation to delete.
     * @return the deleted user invitation.
     */
    UserInvitation delete(UserInvitation userInvitation);

    /**
     * Create a new user invitation.
     *
     * @param userInvitation the user invitation to create.
     * @return the created user invitation.
     */
    UserInvitation create(UserInvitation userInvitation);
}
