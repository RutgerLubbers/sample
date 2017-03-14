package nl.qnh.usermanagement.web.input;

import nl.qnh.usermanagement.model.EmailAddress;
import org.hawaiiframework.web.input.InputConverter;
import org.springframework.stereotype.Component;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 * {@inheritDoc}
 */
@Component
public class EmailAddressInputConverter implements InputConverter<EmailAddressInput, EmailAddress> {

    /**
     * {@inheritDoc}
     */
    @Override
    public EmailAddress convert(final EmailAddressInput input) {
        if (input == null) {
            return null;
        }

        final EmailAddress target = new EmailAddress();
        try {
            target.setAddress(new InternetAddress(input.getEmailAddress()));
        } catch (AddressException e) {
            throw new IllegalArgumentException(
                    String.format("Cannot create an email address (InternetAddress) from '%s'.", input.getEmailAddress()), e);
        }
        target.setConfirmed(input.isConfirmed());

        return target;
    }
}
