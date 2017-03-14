package nl.qnh.usermanagement.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * The user of the application.
 */
@SuppressWarnings("PMD.ShortClassName")
public class User {

    /**
     * The user's id.
     */
    @SuppressWarnings("PMD.ShortVariable")
    private Long id;

    /**
     * The user's SSO unique id (subject id in openid speak).
     */
    private UUID subjectId;

    /**
     * The user's first name.
     */
    private String firstName;

    /**
     * The user's last name.
     */
    private String lastName;

    /**
     * The user's first email address.
     */
    private EmailAddress emailAddress;

    /**
     * The user's roles.
     */
    private final List<String> roles = new ArrayList<>();

    @SuppressWarnings("PMD.CommentRequired") // Accessor
    public Long getId() {
        return id;
    }

    @SuppressWarnings({"PMD.CommentRequired", "PMD.ShortVariable"}) // Accessor
    public void setId(final Long id) {
        this.id = id;
    }

    @SuppressWarnings("PMD.CommentRequired") // Accessor
    public UUID getSubjectId() {
        return subjectId;
    }

    @SuppressWarnings("PMD.CommentRequired") // Accessor
    public void setSubjectId(final UUID subjectId) {
        this.subjectId = subjectId;
    }

    @SuppressWarnings("PMD.CommentRequired") // Accessor
    public String getFirstName() {
        return firstName;
    }

    @SuppressWarnings("PMD.CommentRequired") // Accessor
    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    @SuppressWarnings("PMD.CommentRequired") // Accessor
    public String getLastName() {
        return lastName;
    }

    @SuppressWarnings("PMD.CommentRequired") // Accessor
    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    @SuppressWarnings("PMD.CommentRequired") // Accessor
    public EmailAddress getEmailAddress() {
        return emailAddress;
    }

    /**
     * Returns the string representation of the email address.
     * @return
     */
    public String getEmailAddressAsString() {
        if (emailAddress == null) {
            return null;
        }
        return emailAddress.getAddressAsString();
    }

    @SuppressWarnings("PMD.CommentRequired") // Accessor
    public void setEmailAddress(final EmailAddress emailAddress) {
        this.emailAddress = emailAddress;
    }

    @SuppressWarnings("PMD.CommentRequired")
    public List<String> getRoles() {
        return Collections.unmodifiableList(roles);
    }

    @SuppressWarnings("PMD.CommentRequired")
    public void setRoles(final List<String> roles) {
        this.roles.clear();
        if (roles != null) {
            this.roles.addAll(roles);
        }
    }


}
