package nl.qnh.usermanagement.web.input;

import java.util.List;

/**
 * Input class for user modifications (create/updateUser).
 */
public class UserInput {

    /**
     * The user id.
     */
    @SuppressWarnings("PMD.ShortVariable")
    private Long id;

    /**
     * The user's first name.
     */
    private String firstName;

    /**
     * The user's last name.
     */
    private String lastName;

    /**
     * The user's email address.
     */
    private EmailAddressInput emailAddress;

    /**
     * The user's roles.
     */
    private List<String> roles;

    @SuppressWarnings("PMD.CommentRequired")
    public Long getId() {
        return id;
    }

    @SuppressWarnings({"PMD.CommentRequired", "PMD.ShortVariable"})
    public void setId(final Long id) {
        this.id = id;
    }

    @SuppressWarnings("PMD.CommentRequired")
    public String getFirstName() {
        return firstName;
    }

    @SuppressWarnings("PMD.CommentRequired")
    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    @SuppressWarnings("PMD.CommentRequired")
    public String getLastName() {
        return lastName;
    }

    @SuppressWarnings("PMD.CommentRequired")
    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    @SuppressWarnings("PMD.CommentRequired")
    public EmailAddressInput getEmailAddress() {
        return emailAddress;
    }

    @SuppressWarnings("PMD.CommentRequired")
    public void setEmailAddress(final EmailAddressInput emailAddress) {
        this.emailAddress = emailAddress;
    }

    @SuppressWarnings("PMD.CommentRequired")
    public List<String> getRoles() {
        return roles;
    }

    @SuppressWarnings("PMD.CommentRequired")
    public void setRoles(final List<String> roles) {
        this.roles = roles;
    }
}
