package nl.qnh.usermanagement.web.input;

import org.hawaiiframework.validation.ValidationResult;
import org.hawaiiframework.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.util.Objects.requireNonNull;

/**
 * {@inheritDoc}
 */
@Component
public class CreateUserInputValidator implements Validator<UserInput> {

    /**
     * The email address input validator.
     */
    private final EmailAddressInputValidator emailAddressInputValidator;

    /**
     * Constructor
     */
    @Autowired
    public CreateUserInputValidator(final EmailAddressInputValidator emailAddressInputValidator) {
        this.emailAddressInputValidator = requireNonNull(emailAddressInputValidator);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(final UserInput user, final ValidationResult validationResult) {
        if (user.getId() != null) {
            validationResult.rejectValue("id", "forbidden");
        }

        if (user.getFirstName() == null) {
            validationResult.rejectValue("first_name", "required");
        }

        if (user.getLastName() == null) {
            validationResult.rejectValue("last_name", "required");
        }

        if (user.getEmailAddress() == null) {
            validationResult.rejectValue("email_address", "required");
        } else {
            emailAddressInputValidator.validate(user.getEmailAddress(), validationResult);
        }

        //        if (user.getRoles() == null) {
        //            validationResult.rejectValue("roles", "required");
        //        }
    }
}
