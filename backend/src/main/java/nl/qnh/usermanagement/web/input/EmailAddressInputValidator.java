package nl.qnh.usermanagement.web.input;

import org.hawaiiframework.validation.ValidationResult;
import org.hawaiiframework.validation.Validator;
import org.springframework.stereotype.Component;

/**
 * {@inheritDoc}
 */
@Component
public class EmailAddressInputValidator implements Validator<EmailAddressInput> {

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(final EmailAddressInput input, final ValidationResult validationResult) {
        if (input.isConfirmed() == null) {
            validationResult.rejectValue("confirmed", "required");
        }

        if (input.getEmailAddress() == null) {
            validationResult.rejectValue("email_address", "required");
        }


    }
}
