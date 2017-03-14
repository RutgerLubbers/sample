package nl.qnh.usermanagement.web.controller;

import nl.qnh.usermanagement.model.User;
import nl.qnh.usermanagement.model.UserInvitation;
import nl.qnh.usermanagement.model.UserInvitationResponse;
import nl.qnh.usermanagement.web.input.CreateSsoAccountInput;
import nl.qnh.usermanagement.web.input.CreateSsoAccountInputValidator;
import nl.qnh.usermanagement.web.resource.UserInvitationResourceAssembler;
import nl.qnh.usermanagement.web.resource.UserInvitationResponseResourceAssembler;
import nl.qnh.usermanagement.service.UserInvitationService;
import nl.qnh.usermanagement.service.UserManagementService;
import org.hawaiiframework.web.annotation.Post;
import org.hawaiiframework.web.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static java.util.Objects.requireNonNull;
import static nl.qnh.usermanagement.web.controller.Paths.USER_INVITATION;
import static nl.qnh.usermanagement.web.controller.Paths.USER_INVITATIONS;
import static nl.qnh.usermanagement.web.controller.Paths.USER_INVITATION_CREATE_ACCOUNT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * The User Invitation REST controller.
 */
@RestController
public class UserInvitationController {

    /**
     * The user invitation service.
     */
    private final UserInvitationService userInvitationService;

    /**
     * The user management service.
     */
    private final UserManagementService userManagementService;

    /**
     * The create sso account input validator.
     */
    private final CreateSsoAccountInputValidator createSsoAccountInputValidator;

    /**
     * The UserInvitation resource assembler.
     */
    @SuppressWarnings("PMD.LongVariable")
    private final UserInvitationResourceAssembler userInvitationResourceAssembler;

    /**
     * The UserInvitationResponse resource assembler.
     */
    @SuppressWarnings("PMD.LongVariable")
    private final UserInvitationResponseResourceAssembler userInvitationResponseResourceAssembler;

    /**
     * Constructor
     */
    @SuppressWarnings("PMD.LongVariable")
    @Autowired
    public UserInvitationController(final CreateSsoAccountInputValidator createSsoAccountInputValidator
            , final UserInvitationService userInvitationService
            , final UserManagementService userManagementService
            , final UserInvitationResourceAssembler userInvitationResourceAssembler
            , final UserInvitationResponseResourceAssembler userInvitationResponseResourceAssembler
    ) {
        this.createSsoAccountInputValidator = requireNonNull(createSsoAccountInputValidator);
        this.userInvitationService = requireNonNull(userInvitationService);
        this.userManagementService = requireNonNull(userManagementService);
        this.userInvitationResourceAssembler = requireNonNull(userInvitationResourceAssembler);
        this.userInvitationResponseResourceAssembler = requireNonNull(userInvitationResponseResourceAssembler);
    }

    /**
     * Create an invitation for a user.
     *
     * @param userId the user to create an invitation for.
     * @return the created invitation.
     */
    @SuppressWarnings("PMD.LawOfDemeter")
    @Post(path = USER_INVITATIONS, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUserInvitation(@PathVariable final Long userId) {
        final User user = userManagementService.getUser(userId).orElseThrow(ResourceNotFoundException::new);

        final UserInvitation userInvitation = userInvitationService.createUserInvitation(user);

        return ResponseEntity.ok().body(userInvitationResourceAssembler.toResource(userInvitation));
    }

    /**
     * Accept an invite and link the user to the SSO.
     *
     * @param invitationId the invitation to accept.
     * @return ...
     */
    @SuppressWarnings("PMD.LawOfDemeter")
    @Post(path = USER_INVITATION, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> acceptUserInvitation(@PathVariable final String invitationId) {
        final UserInvitationResponse userInvitationResponse = userInvitationService.acceptUserInvitation(UUID.fromString(invitationId));

        return ResponseEntity.ok().body(userInvitationResponseResourceAssembler.toResource(userInvitationResponse));
    }

    /**
     * Accept an invite, create a new SSO account and link the user to the SSO.
     *
     * @param invitationId the invitation to accept.
     * @return ...
     */
    @SuppressWarnings("PMD.LawOfDemeter")
    @Post(path = USER_INVITATION_CREATE_ACCOUNT, produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> acceptUserInvitationAndCreateSsoUser(@PathVariable final String invitationId
            , @RequestBody final CreateSsoAccountInput createSsoAccountInput) {
        createSsoAccountInputValidator.validateAndThrow(createSsoAccountInput);

        final UserInvitationResponse userInvitationResponse = userInvitationService
                .createSsoAccountAndAcceptInvitation(UUID.fromString(invitationId), createSsoAccountInput.getPassword());

        return ResponseEntity.ok().body(userInvitationResponseResourceAssembler.toResource(userInvitationResponse));
    }
}
