package nl.qnh.usermanagement.web.input;

/**
 * Resource representing an email address.
 */
public class EmailAddressInput {

    /**
     * Has the email address been confirmed?
     */
    private Boolean confirmed = false;
    /**
     * The actual email address.
     */
    private String emailAddress;

    @SuppressWarnings("PMD.CommentRequired")
    public Boolean isConfirmed() {
        return confirmed;
    }

    @SuppressWarnings("PMD.CommentRequired")
    public void setConfirmed(final Boolean confirmed) {
        this.confirmed = confirmed;
    }

    @SuppressWarnings("PMD.CommentRequired")
    public String getEmailAddress() {
        return emailAddress;
    }

    @SuppressWarnings("PMD.CommentRequired")
    public void setEmailAddress(final String emailAddress) {
        this.emailAddress = emailAddress;
    }

}
