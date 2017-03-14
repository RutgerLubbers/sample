package nl.qnh.usermanagement.web.input;

/**
 * Input class for user modifications (create/updateUser).
 */
public class CreateSsoAccountInput {

    /**
     * The user's password.
     */
    private String password;

    @SuppressWarnings("PMD.CommentRequired")
    public String getPassword() {
        return password;
    }

    @SuppressWarnings("PMD.CommentRequired")
    public void setPassword(final String password) {
        this.password = password;
    }
}
