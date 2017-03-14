package nl.qnh.usermanagement.web.resource;

/**
 * Resource variant of UserInvitation.
 */
public class UserInvitationResource {

    /**
     * The user invitation's id.
     */
    @SuppressWarnings("PMD.ShortVariable")
    private String id;

    @SuppressWarnings("PMD.CommentRequired")
    public String getId() {
        return id;
    }

    @SuppressWarnings({"PMD.CommentRequired", "PMD.ShortVariable"})
    public void setId(final String id) {
        this.id = id;
    }
}
