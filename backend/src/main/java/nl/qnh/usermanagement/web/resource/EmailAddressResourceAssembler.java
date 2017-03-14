package nl.qnh.usermanagement.web.resource;

import nl.qnh.usermanagement.model.EmailAddress;
import org.hawaiiframework.web.resource.ResourceAssembler;
import org.springframework.stereotype.Component;

import static java.util.Objects.requireNonNull;

/**
 * {@inheritDoc}
 */
@Component
public class EmailAddressResourceAssembler implements ResourceAssembler<EmailAddress, EmailAddressResource> {

    /**
     * {@inheritDoc}
     */
    @Override
    public EmailAddressResource toResource(final EmailAddress source) {
        requireNonNull(source);

        final EmailAddressResource target = new EmailAddressResource();
        target.setConfirmed(source.isConfirmed());
        target.setEmailAddress(source.getAddressAsString());
        return target;
    }
}
