package nl.qnh.usermanagement.web.resource;

/**
 * Resource representing an email address.
 */
public class EmailAddressResource {

    /**
     * Has the email address been confirmed?
     */
    private boolean confirmed = false;
    /**
     * The actual email address.
     */
    private String emailAddress;

    @SuppressWarnings("PMD.CommentRequired")
    public boolean isConfirmed() {
        return confirmed;
    }

    @SuppressWarnings("PMD.CommentRequired")
    public void setConfirmed(final boolean confirmed) {
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
