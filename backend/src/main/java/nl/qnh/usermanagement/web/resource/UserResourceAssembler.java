package nl.qnh.usermanagement.web.resource;

import nl.qnh.usermanagement.model.User;
import org.hawaiiframework.web.resource.ResourceAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.util.Objects.requireNonNull;

/**
 * {@inheritDoc}
 */
@Component
public class UserResourceAssembler implements ResourceAssembler<User, UserResource> {

    /**
     * Resource assembler for email address.
     */
    private final EmailAddressResourceAssembler emailAddressResourceAssembler;

    /**
     * Constructor
     *
     * @param emailAddressResourceAssembler Assembler for email addresses
     */
    @Autowired
    public UserResourceAssembler(final EmailAddressResourceAssembler emailAddressResourceAssembler) {
        this.emailAddressResourceAssembler = requireNonNull(emailAddressResourceAssembler);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserResource toResource(final User source) {
        if (source == null) {
            return null;
        }

        final UserResource target = new UserResource();
        target.setEmailAddress(emailAddressResourceAssembler.toResource(source.getEmailAddress()));
        target.setFirstName(source.getFirstName());
        target.setId(source.getId());
        target.setLastName(source.getLastName());
        target.setRoles(source.getRoles());
        return target;
    }
}
