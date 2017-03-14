package nl.qnh.usermanagement.model;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * An invitation for a User to create an account and use the site.
 */
public class UserInvitation {

    /**
     * The invitation's id.
     */
    @SuppressWarnings("PMD.ShortVariable")
    private UUID id;

    /**
     * The invited user.
     */
    private User user;

    /**
     * The create date.
     */
    private LocalDateTime created;

    /**
     * The expiry date.
     */
    private LocalDateTime expires;

    @SuppressWarnings("PMD.CommentRequired")
    public UUID getId() {
        return id;
    }

    @SuppressWarnings({"PMD.CommentRequired", "PMD.ShortVariable"})
    public void setId(final UUID id) {
        this.id = id;
    }

    @SuppressWarnings("PMD.CommentRequired")
    public User getUser() {
        return user;
    }

    @SuppressWarnings("PMD.CommentRequired")
    public void setUser(final User user) {
        this.user = user;
    }

    @SuppressWarnings("PMD.CommentRequired")
    public LocalDateTime getCreated() {
        return created;
    }

    @SuppressWarnings("PMD.CommentRequired")
    public void setCreated(final LocalDateTime created) {
        this.created = created;
    }

    @SuppressWarnings("PMD.CommentRequired")
    public LocalDateTime getExpires() {
        return expires;
    }

    @SuppressWarnings("PMD.CommentRequired")
    public void setExpires(final LocalDateTime expires) {
        this.expires = expires;
    }

    /**
     * Check whether the invitation is expired.
     */
    @SuppressWarnings("PMD.LawOfDemeter") // fluent api
    public boolean isExpired() {
        return getExpires() == null || LocalDateTime.now().isAfter(getExpires());
    }
}
