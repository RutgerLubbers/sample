package nl.qnh.usermanagement.service;

import nl.qnh.usermanagement.config.UserManagementConfiguration;
import nl.qnh.usermanagement.model.User;
import nl.qnh.usermanagement.model.UserInvitation;
import nl.qnh.usermanagement.model.UserInvitationResponse;
import nl.qnh.usermanagement.repository.UserInvitationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

/**
 * Service to (re)send / receive user invitations.
 */
@SuppressWarnings("PMD.TooManyMethods")
@Component
public class UserInvitationService {

    /**
     * The logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UserInvitationService.class);

    /**
     * The user invitation repository.
     */
    private final UserInvitationRepository userInvitationRepository;

    /**
     * The user management service.
     */
    private final UserManagementService userManagementService;

    /**
     * The user management configuration.
     */
    private final UserManagementConfiguration configuration;

    /**
     * Constructor.
     */
    @Autowired
    public UserInvitationService(final UserInvitationRepository userInvitationRepository
            , final UserManagementService userManagementService
            , final UserManagementConfiguration configuration) {
        this.userInvitationRepository = requireNonNull(userInvitationRepository);
        this.userManagementService = requireNonNull(userManagementService);
        this.configuration = requireNonNull(configuration);
    }


    /**
     * Create a new user invitation.
     *
     * @param user the user to invite.
     */
    @SuppressWarnings("PMD.LawOfDemeter") // fluent api
    public UserInvitation createUserInvitation(final User user) {
        final UserInvitation userInvitation = new UserInvitation();
        userInvitation.setUser(user);
        userInvitation.setCreated(LocalDateTime.now());
        final Integer expiresInDays = configuration.getUserInvitationExpiresInDays();
        if (expiresInDays != null) {
            userInvitation.setExpires(LocalDateTime.now().plusDays(expiresInDays));
        }
        return userInvitationRepository.create(userInvitation);
    }

    private Optional<User> loadUser(final User user) {
        return userManagementService.getUser(user.getId());
    }

    @SuppressWarnings("PMD.LawOfDemeter")
    private void addUser(final UserInvitation userInvitation) {
        userInvitation.setUser(loadUser(userInvitation.getUser()).orElse(null));
    }

    /**
     * Find the user invitation by it's id.
     *
     * @param userInvitationId The id to search for.
     * @return THe user invitation.
     */
    public UserInvitation findById(final UUID userInvitationId) {
        final UserInvitation userInvitation = userInvitationRepository.findById(userInvitationId);

        addUser(userInvitation);

        return userInvitation;
    }

    /**
     * @see #acceptUserInvitation(UserInvitation)
     */
    public UserInvitationResponse acceptUserInvitation(final UUID userInvitationId) {
        return acceptUserInvitation(findById(userInvitationId));
    }

    /**
     * Accept the user invitation.
     *
     * @param userInvitation the user's invitation
     * @return a response object with various flags / messages.
     */
    @SuppressWarnings("PMD.LawOfDemeter")
    public UserInvitationResponse acceptUserInvitation(final UserInvitation userInvitation) {
        final UserInvitationResponse response = checkUserInvitation(userInvitation);

        if (!response.passedChecks()) {
            return response;
        }

        final User user = response.getUser();

        userManagementService.linkUserToSsoUser(user);

        userInvitationRepository.delete(userInvitation);

        return response;
    }

    /**
     * Create an SSO account and accept the invitation.
     */
    public UserInvitationResponse createSsoAccountAndAcceptInvitation(final UUID userInvitationId, final String password) {
        return createSsoAccountAndAcceptInvitation(userInvitationRepository.findById(userInvitationId), password);
    }

    /**
     * Create an SSO account and accept the invitation.
     */
    @SuppressWarnings("PMD.LawOfDemeter")
    public UserInvitationResponse createSsoAccountAndAcceptInvitation(final UserInvitation userInvitation, final String password) {
        final UserInvitationResponse response = checkUserInvitationForCreateAccount(userInvitation);

        if (!response.passedChecks()) {
            return response;
        }

        final User user = response.getUser();
        userManagementService.createSsoUser(user, password);

        return acceptUserInvitation(userInvitation);
    }

    @SuppressWarnings("PMD.LawOfDemeter")
    private UserInvitationResponse checkCommonErrors(final UserInvitation userInvitation) {
        final UserInvitationResponse response = new UserInvitationResponse();

        if (userInvitation == null) {
            LOGGER.debug("UserInvitation '{}' does not exist.", userInvitation.getId());
            response.setUserInvitationNotFound(true);
            return response;
        }

        if (userInvitation.isExpired()) {
            LOGGER.debug("UserInvitation '{}' is expired.", userInvitation.getId());
            response.setUserInvitationExpired(true);
            return response;
        }

        final User invitedUser = userInvitation.getUser();
        if (invitedUser.getId() == null) {
            LOGGER.debug("UserInvitation '{}' does not have a user.", userInvitation.getId(), invitedUser.getId());
            response.setUserDoesNotExist(true);
            return response;
        }

        final User user = loadUser(invitedUser).orElse(null);
        if (user == null) {
            LOGGER.debug("UserInvitation '{}' does not have an existing user '{}'.", userInvitation.getId(), invitedUser.getId());
            response.setUserDoesNotExist(true);
            return response;
        }
        response.setUser(user);

        return response;
    }

    @SuppressWarnings("PMD.LawOfDemeter")
    private UserInvitationResponse checkUserInvitation(final UserInvitation userInvitation) {
        final UserInvitationResponse response = checkCommonErrors(userInvitation);

        if (!response.passedChecks()) {
            return response;
        }

        final User user = response.getUser();
        if (!userManagementService.userExistsInSso(user)) {
            LOGGER.debug("User '{}' does not exist in SSO.", user.getEmailAddressAsString());
            response.setUserDoesNotExistInSso(true);
            return response;
        }

        return response;
    }

    @SuppressWarnings("PMD.LawOfDemeter")
    private UserInvitationResponse checkUserInvitationForCreateAccount(final UserInvitation userInvitation) {
        final UserInvitationResponse response = checkCommonErrors(userInvitation);

        if (!response.passedChecks()) {
            return response;
        }

        final User user = response.getUser();

        if (userManagementService.userExistsInSso(user)) {
            LOGGER.debug("Create user '{}' while it already exists.", user.getEmailAddressAsString());
            response.setDuplicateUserInSso(true);
            return response;
        }

        return response;
    }


}
