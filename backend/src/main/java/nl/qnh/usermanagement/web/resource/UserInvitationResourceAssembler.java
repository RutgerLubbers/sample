package nl.qnh.usermanagement.web.resource;

import nl.qnh.usermanagement.model.UserInvitation;
import org.hawaiiframework.web.resource.ResourceAssembler;
import org.springframework.stereotype.Component;

import static java.util.Objects.requireNonNull;

/**
 * {@inheritDoc}
 */
@Component
public class UserInvitationResourceAssembler implements ResourceAssembler<UserInvitation, UserInvitationResource> {

    /**
     * The uuid resource assembler.
     */
    private final UUIDResourceAssembler uuidResourceAssembler;

    /**
     * The constructor
     */
    public UserInvitationResourceAssembler(final UUIDResourceAssembler uuidResourceAssembler) {
        this.uuidResourceAssembler = requireNonNull(uuidResourceAssembler);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("PMD.LawOfDemeter")
    @Override
    public UserInvitationResource toResource(final UserInvitation source) {
        requireNonNull(source);

        final UserInvitationResource target = new UserInvitationResource();
        target.setId(uuidResourceAssembler.toResource(source.getId()));

        return target;
    }
}
