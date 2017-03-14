package nl.qnh.usermanagement.web.input;

/**
 * Rest input for changing a user's password.
 */
public class ChangePasswordInput {

    /**
     * The user's current password.
     */
    private String currentPassword;

    /**
     * The requested new password.
     */
    private String newPassword;

    @SuppressWarnings("PMD.CommentRequired")
    public String getCurrentPassword() {
        return currentPassword;
    }

    @SuppressWarnings("PMD.CommentRequired")
    public void setCurrentPassword(final String currentPassword) {
        this.currentPassword = currentPassword;
    }

    @SuppressWarnings("PMD.CommentRequired")
    public String getNewPassword() {
        return newPassword;
    }

    @SuppressWarnings("PMD.CommentRequired")
    public void setNewPassword(final String newPassword) {
        this.newPassword = newPassword;
    }
}
