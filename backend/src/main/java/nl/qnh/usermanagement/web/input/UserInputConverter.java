package nl.qnh.usermanagement.web.input;

import nl.qnh.usermanagement.model.User;
import org.hawaiiframework.web.input.InputConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.util.Objects.requireNonNull;

/**
 * {@inheritDoc}
 */
@Component
public class UserInputConverter implements InputConverter<UserInput, User> {

    /**
     *
     */
    private final EmailAddressInputConverter emailAddressInputConverter;

    /**
     * Constructor.
     */
    @Autowired
    public UserInputConverter(final EmailAddressInputConverter emailAddressInputConverter) {
        this.emailAddressInputConverter = requireNonNull(emailAddressInputConverter);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User convert(final UserInput input) {
        if (input == null) {
            return null;
        }

        final User target = new User();
        target.setId(input.getId());
        target.setFirstName(input.getFirstName());
        target.setLastName(input.getLastName());
        target.setEmailAddress(emailAddressInputConverter.convert(input.getEmailAddress()));
        target.setRoles(input.getRoles());
        return target;
    }
}
