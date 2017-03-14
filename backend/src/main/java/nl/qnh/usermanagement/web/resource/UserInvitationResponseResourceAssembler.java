package nl.qnh.usermanagement.web.resource;

import nl.qnh.usermanagement.model.UserInvitationResponse;
import org.hawaiiframework.web.resource.ResourceAssembler;
import org.springframework.stereotype.Component;

import static java.util.Objects.requireNonNull;

/**
 * {@inheritDoc}
 */
@Component
public class UserInvitationResponseResourceAssembler implements ResourceAssembler<UserInvitationResponse, UserInvitationResponseResource> {

    /**
     * The user resource assembler.
     */
    private final UserResourceAssembler userResourceAssembler;

    /**
     * The constructor.
     */
    public UserInvitationResponseResourceAssembler(final UserResourceAssembler userResourceAssembler) {
        this.userResourceAssembler = requireNonNull(userResourceAssembler);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserInvitationResponseResource toResource(final UserInvitationResponse source) {
        final UserInvitationResponseResource target = new UserInvitationResponseResource();

        target.setUserDoesNotExist(source.isUserDoesNotExist());
        target.setUserDoesNotExistInSso(source.isUserDoesNotExistInSso());
        target.setDuplicateUserInSso(source.isDuplicateUserInSso());
        target.setUserInvitationExpired(source.isUserInvitationExpired());
        target.setUserInvitationNotFound(source.isUserInvitationNotFound());

        target.setInvitationAccepted(source.passedChecks());

        target.setUser(userResourceAssembler.toResource(source.getUser()));

        return target;
    }
}
