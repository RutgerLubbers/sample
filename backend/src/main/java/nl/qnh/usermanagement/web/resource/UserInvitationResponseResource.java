package nl.qnh.usermanagement.web.resource;

/**
 * Class to signal the different responses to an 'accept user invitation'.
 */
public class UserInvitationResponseResource {

    /**
     * Does the invitation exist?
     */
    private boolean userInvitationNotFound;

    /**
     * Does the user exist?
     */
    private boolean userDoesNotExist;

    /**
     * Does the user exist in the sso?
     */
    private boolean userDoesNotExistInSso;

    /**
     * Does the user already exist in the sso?
     */
    private boolean duplicateUserInSso;

    /**
     * Is the user invitation expired?
     */
    private boolean userInvitationExpired;

    /**
     * Invitation was accepted.
     */
    private boolean invitationAccepted;

    /**
     * The user that was invited.
     */
    private UserResource user;

    @SuppressWarnings("PMD.CommentRequired")
    public boolean isUserInvitationNotFound() {
        return userInvitationNotFound;
    }

    @SuppressWarnings("PMD.CommentRequired")
    public void setUserInvitationNotFound(final boolean userInvitationNotFound) {
        this.userInvitationNotFound = userInvitationNotFound;
    }


    @SuppressWarnings("PMD.CommentRequired")
    public boolean isUserDoesNotExist() {
        return userDoesNotExist;
    }

    @SuppressWarnings("PMD.CommentRequired")
    public void setUserDoesNotExist(final boolean userDoesNotExist) {
        this.userDoesNotExist = userDoesNotExist;
    }

    @SuppressWarnings("PMD.CommentRequired")
    public boolean isUserDoesNotExistInSso() {
        return userDoesNotExistInSso;
    }

    @SuppressWarnings("PMD.CommentRequired")
    public void setUserDoesNotExistInSso(final boolean userDoesNotExistInSso) {
        this.userDoesNotExistInSso = userDoesNotExistInSso;
    }

    @SuppressWarnings("PMD.CommentRequired")
    public boolean isUserInvitationExpired() {
        return userInvitationExpired;
    }

    @SuppressWarnings("PMD.CommentRequired")
    public void setUserInvitationExpired(final boolean userInvitationExpired) {
        this.userInvitationExpired = userInvitationExpired;
    }

    @SuppressWarnings("PMD.CommentRequired")
    public boolean isDuplicateUserInSso() {
        return duplicateUserInSso;
    }

    @SuppressWarnings("PMD.CommentRequired")
    public void setDuplicateUserInSso(final boolean duplicateUserInSso) {
        this.duplicateUserInSso = duplicateUserInSso;
    }

    @SuppressWarnings("PMD.CommentRequired")
    public boolean isInvitationAccepted() {
        return invitationAccepted;
    }

    @SuppressWarnings("PMD.CommentRequired")
    public void setInvitationAccepted(final boolean invitationAccepted) {
        this.invitationAccepted = invitationAccepted;
    }

    @SuppressWarnings("PMD.CommentRequired")
    public UserResource getUser() {
        return user;
    }

    @SuppressWarnings("PMD.CommentRequired")
    public void setUser(final UserResource user) {
        this.user = user;
    }

}
