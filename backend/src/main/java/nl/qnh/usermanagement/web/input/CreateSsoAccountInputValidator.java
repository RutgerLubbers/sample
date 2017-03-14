package nl.qnh.usermanagement.web.input;

import org.hawaiiframework.validation.ValidationResult;
import org.hawaiiframework.validation.Validator;
import org.springframework.stereotype.Component;

/**
 * {@inheritDoc}
 */
@Component
public class CreateSsoAccountInputValidator implements Validator<CreateSsoAccountInput> {

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(final CreateSsoAccountInput input, final ValidationResult validationResult) {
        if (input.getPassword() == null) {
            validationResult.rejectValue("password", "required");
        }

    }
}
