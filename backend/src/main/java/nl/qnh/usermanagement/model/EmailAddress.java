package nl.qnh.usermanagement.model;

import javax.mail.Address;

/**
 * Represents an email address in either a confirmed
 * or an unconfirmed state.
 */
public class EmailAddress {

    /**
     * The confirmed flag.
     */
    private Boolean confirmed = false;

    /**
     * The email address.
     */
    private Address address;

    @SuppressWarnings("PMD.CommentRequired")
    public Boolean isConfirmed() {
        return confirmed;
    }

    @SuppressWarnings("PMD.CommentRequired")
    public void setConfirmed(final Boolean confirmed) {
        this.confirmed = confirmed;
    }

    @SuppressWarnings("PMD.CommentRequired")
    public Address getAddress() {
        return address;
    }

    /**
     * Convenience method to return the email address as a String.
     *
     * @return the email address String
     */
    public String getAddressAsString() {
        return address.toString();
    }

    @SuppressWarnings("PMD.CommentRequired")
    public void setAddress(final Address address) {
        this.address = address;
    }
}
