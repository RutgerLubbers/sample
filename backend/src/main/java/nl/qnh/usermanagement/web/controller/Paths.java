package nl.qnh.usermanagement.web.controller;

/**
 * Class that holds the paths for the controllers.
 */
public class Paths {

    /**
     * The base part of all rest API paths.
     */
    private static final String BASE_PATH = "/rest/v1";

    /**
     * The path for users. (And creation of an instance.)
     */
    public static final String USERS = BASE_PATH + "/users";

    /**
     * The path for a single user.
     */
    public static final String USER = USERS + "/{userId}";

    /**
     * The path for a single user.
     */
    public static final String CHANGE_PASSWORD = USERS + "/{userId}/password";

    /**
     * The path for a single user's invitations
     */
    public static final String USER_INVITATIONS = USERS + "{userId}/invitations";

    /**
     * The path for a singe user invitation.
     */
    public static final String USER_INVITATION = USERS + "/invitations/{invitationId}";

    /**
     * The path for a singe user invitation, with account creation.
     */
    public static final String USER_INVITATION_CREATE_ACCOUNT = USER_INVITATION + "/create-account";
}
